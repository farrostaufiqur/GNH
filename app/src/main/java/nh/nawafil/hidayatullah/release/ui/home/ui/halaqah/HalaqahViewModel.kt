package nh.nawafil.hidayatullah.release.ui.home.ui.halaqah

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nh.nawafil.hidayatullah.release.data.network.api.ApiConfig
import nh.nawafil.hidayatullah.release.data.network.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HalaqahViewModel : ViewModel(){

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    private val _authorized = MutableLiveData<Boolean?>()
    val authorized: LiveData<Boolean?> = _authorized

    private val _group = MutableLiveData<HalaqahGroupItem?>()
    val group: LiveData<HalaqahGroupItem?> = _group

    private val _member = MutableLiveData<List<HalaqahMemberItem?>?>()
    val member: LiveData<List<HalaqahMemberItem?>?> = _member

    private val _statusGroup = MutableLiveData<Boolean?>()
    val statusGroup: LiveData<Boolean?> = _statusGroup

    private val _statusMember = MutableLiveData<Boolean?>()
    val statusMember: LiveData<Boolean?> = _statusMember

    private val _statusReq = MutableLiveData<Boolean?>()
    val statusReq: LiveData<Boolean?> = _statusReq

    fun getHalaqah(halaqah: String){
        val params = HashMap<String?, Any?>()
        params["halaqah"] = halaqah
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getHalaqahGroup(params)
            client.enqueue(object : Callback<HalaqahGroupResponse> {
                override fun onResponse(
                    call: Call<HalaqahGroupResponse>,
                    response: Response<HalaqahGroupResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        when (responseBody?.status) {
                            200 -> {
                                _group.value = responseBody.data
                                _statusGroup.value = true
                            }
                            404 -> {
                                _statusGroup.value = false
                            }
                        }
                    } else {
                        _statusGroup.value = false
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<HalaqahGroupResponse>, t: Throwable) {
                    _statusGroup.value = false
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun getMember(id: String, token: String, halaqah: String, date: String? = null){
        val params = HashMap<String?, Any?>()
        params["id"] = id
        params["token"] = token
        params["halaqah"] = halaqah
        if (date != null){
            params["date"] = date
        }
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getHalaqahMember(params)
            client.enqueue(object : Callback<HalaqahMemberResponse> {
                override fun onResponse(
                    call: Call<HalaqahMemberResponse>,
                    response: Response<HalaqahMemberResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        when (responseBody?.status) {
                            200 -> {
                                _member.value = responseBody.data
                                _statusMember.value = true
                            }
                            404 -> {
                                _statusMember.value = false
                            }
                        }
                    } else {
                        _statusMember.value = false
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<HalaqahMemberResponse>, t: Throwable) {
                    _statusMember.value = false
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun createHalaqah(id: String, token: String, name: String){
        val params = HashMap<String?, Any?>()
        params["id"] = id
        params["token"] = token
        params["name"] = name
        viewModelScope.launch {
            val client = ApiConfig.getApiService().createHalaqah(params)
            client.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        when (responseBody?.status) {
                            200 -> {
                                _statusReq.value = true
                            }
                            404 -> {
                                _statusReq.value = false
                            }
                            400 -> {
                                _statusReq.value = false
                            }
                        }
                    } else {
                        _statusReq.value = false
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _statusReq.value = false
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun joinHalaqah(id: String, token: String, halaqah: String){
        val params = HashMap<String?, Any?>()
        params["id"] = id
        params["token"] = token
        params["halaqah"] = halaqah
        viewModelScope.launch {
            val client = ApiConfig.getApiService().joinHalaqah(params)
            client.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        when (responseBody?.status) {
                            200 -> {
                                _statusReq.value = true
                            }
                            404 -> {
                                _statusReq.value = false
                            }
                        }
                    } else {
                        _statusReq.value = false
                        _message.value = "Unknown Problem"
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _statusReq.value = false
                    _message.value = "Unknown Problem"
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object{
        const val TAG = "HalaqahViewModel"
    }
}