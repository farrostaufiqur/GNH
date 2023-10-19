package nh.nawafil.hidayatullah.release.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nh.nawafil.hidayatullah.release.data.network.api.ApiConfig
import nh.nawafil.hidayatullah.release.data.network.response.UserResponse
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val pref: AppPreferences) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean?>()
    val isSuccess: MutableLiveData<Boolean?> = _isSuccess

    private val _message = MutableLiveData<String?>()
    val message: MutableLiveData<String?> = _message

    fun login(email: String, password: String){
        viewModelScope.launch {
            val params = HashMap<String?, String?>()
            params["email"] = email
            params["password"] = password

            _isLoading.value = true
            _isSuccess.value = null
            val client = ApiConfig.getApiService().loginUser(params)
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        when (response.body()?.status) {
                            1 -> {
                                val responseData = response.body()!!.data
                                saveInfo(responseData?.idUser, responseData?.token, responseData?.name, responseData?.email, responseData?.idHalaqah
                                )
                                _message.value = "Login Success"
                                _isSuccess.value = true
                            }
                            0 -> {
                                _isSuccess.value = false
                                _message.value = "Login Failed"
                            }
                        }
                    } else {
                        _isSuccess.value = false
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isSuccess.value = false
                    _isLoading.value = false
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun saveInfo(id: String?, token: String?, name: String?, email: String?, halaqah: String?){
        viewModelScope.launch {
            pref.saveUserName(name!!)
            pref.saveUserEmail(email!!)
            if (halaqah != null){
                pref.saveUserHalaqah(halaqah)
            }
            pref.saveUserId(id!!)
            pref.saveUserToken(token!!)
            pref.saveUserTable(setOf("0","0","0","0","0","0","0"))
        }
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}