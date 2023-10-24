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

    fun login(username: String, password: String){
        viewModelScope.launch {
            val params = HashMap<String?, String?>()
            params["username"] = username
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
                            200 -> {
                                val responseBody = response.body()!!.data
                                saveInfo(responseBody?.id, responseBody?.token)
                                _message.value = "Login Success"
                                _isSuccess.value = true
                            }
                            404 -> {
                                _isSuccess.value = false
                                _message.value = "Wrong password or Username not found"
                            }
                            403 -> {
                                _isSuccess.value = false
                                _message.value = "Wrong password or Username not found"
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

    fun saveInfo(id: String?, token: String?){
        viewModelScope.launch {
            pref.saveUserId(id!!)
            pref.saveUserToken(token!!)
        }
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}