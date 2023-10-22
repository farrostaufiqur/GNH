package nh.nawafil.hidayatullah.release.ui.home.ui.nawafil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nh.nawafil.hidayatullah.release.data.network.api.ApiConfig
import nh.nawafil.hidayatullah.release.data.network.response.ApiResponse
import nh.nawafil.hidayatullah.release.data.network.response.NawafilResponseSingle
import nh.nawafil.hidayatullah.release.data.network.response.NawafilSingleItem
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NawafilViewModel : ViewModel() {
    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    private val _getStatus = MutableLiveData<Boolean?>()
    val getStatus: LiveData<Boolean?> = _getStatus

    private val _postStatus = MutableLiveData<Boolean?>()
    val postStatus: LiveData<Boolean?> = _postStatus

    private val _nawafil = MutableLiveData<NawafilSingleItem?>()
    val nawafil: LiveData<NawafilSingleItem?> = _nawafil

    fun getNawafil(params : HashMap<String?, Any?>){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getNawafil(params)
            client.enqueue(object : Callback<NawafilResponseSingle> {
                override fun onResponse(
                    call: Call<NawafilResponseSingle>,
                    response: Response<NawafilResponseSingle>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            when (responseBody.status) {
                                200 -> {
                                    _nawafil.value = responseBody.data
                                    _getStatus.value = true
                                }
                                404 -> {
                                    _getStatus.value = false
                                }
                            }
                        }

                    } else {
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<NawafilResponseSingle>, t: Throwable) {
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun postNawafil(params : HashMap<String?, Any?>){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().postNawafil(params)
            client.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.status) {
                            200 -> {
                                _getStatus.value = true
                            }
                            404 -> {
                                _getStatus.value = false
                            }
                        }
                    } else {
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object{
        const val TAG = "NawafilViewModel"
    }
}