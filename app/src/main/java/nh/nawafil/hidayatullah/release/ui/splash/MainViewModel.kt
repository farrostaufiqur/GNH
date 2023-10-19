package nh.nawafil.hidayatullah.release.ui.splash

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import nh.nawafil.hidayatullah.release.data.network.api.ApiConfig
import nh.nawafil.hidayatullah.release.data.network.response.UserItem
import nh.nawafil.hidayatullah.release.data.network.response.UserResponse
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val pref: AppPreferences) : ViewModel(){
    fun getUserId(): LiveData<String?> = pref.getUserId().asLiveData()
    fun getUserToken(): LiveData<String?> = pref.getUserToken().asLiveData()
    fun getTheme(): LiveData<Boolean?> = pref.getTheme().asLiveData()

    private val _authorized = MutableLiveData<Boolean?>()
    val authorized: MutableLiveData<Boolean?> = _authorized

    private val _user = MutableLiveData<UserItem?>()
    val user: MutableLiveData<UserItem?> = _user

    fun getUser(id: String?, token: String?){
        viewModelScope.launch {
            val params = HashMap<String?, String?>()
            params["id"] = id
            params["token"] = token

            _authorized.value = null
            val client = ApiConfig.getApiService().getUser(params)
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.status) {
                            200 -> {
                                _user.value = response.body()!!.data
                                _authorized.value = true
                            }
                            403 -> {
                                _authorized.value = false
                            }
                            404 -> {
                                _authorized.value = false
                            }
                            400 -> {
                                _authorized.value = false
                            }
                        }
                    } else {
                        _authorized.value = false
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _authorized.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }
    companion object{
        private const val TAG = "MainViewModel"
    }
}