package nh.nawafil.hidayatullah.release.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nh.nawafil.hidayatullah.release.data.network.api.ApiConfig
import nh.nawafil.hidayatullah.release.data.network.response.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean?>()
    val isSuccess: MutableLiveData<Boolean?> = _isSuccess

    private val _message = MutableLiveData<String?>()
    val message: MutableLiveData<String?> = _message

    fun register(name: String, email: String, password: String){
        viewModelScope.launch {
            val params = HashMap<String?, String?>()
            params["name"] = name
            params["email"] = email
            params["password"] = password

            _isLoading.value = true
            _isSuccess.value = null
            val client = ApiConfig.getApiService().registerUser(params)
            client.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        when (response.body()?.status) {
                            1 -> {
                                _message.value = "Register is Success"
                                _isSuccess.value = true
                            }
                            2 -> {
                                _isSuccess.value = false
                                _message.value = "Account is Already Registered"
                            }
                            0 -> {
                                _isSuccess.value = false
                                _message.value = "Register is Failed"
                            }
                        }
                    } else {
                        _isSuccess.value = false
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _isSuccess.value = false
                    _isLoading.value = false
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }
}