package nh.nawafil.hidayatullah.release.data.network.api

import nh.nawafil.hidayatullah.release.data.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //USER API
    @FormUrlEncoded
    @POST("user/login")
    fun loginUser(
        @FieldMap params: HashMap<String?, String?>
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/create")
    fun registerUser(
        @FieldMap params: HashMap<String?, String?>
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("user/update")
    fun updateUser(
        @FieldMap params: HashMap<String?, String?>
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("user/select")
    fun getUser(
        @FieldMap params: HashMap<String?, String?>
    ): Call<UserResponse>

    //NAWAFIL API
    @FormUrlEncoded
    @POST("nawafil/update")
    fun postNawafil(
        @FieldMap params: HashMap<String?, Any?>
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("nawafil/select")
    fun getAllNawafil(
        @FieldMap params: HashMap<String?, String?>
    ): Call<NawafilResponse>

    @FormUrlEncoded
    @POST("nawafil/select")
    fun getNawafil(
        @FieldMap params: HashMap<String?, Any?>
    ): Call<NawafilResponseSingle>

    //HALAQAH API
    @FormUrlEncoded
    @POST("halaqah/select")
    fun getHalaqahGroup(
        @FieldMap params: HashMap<String?, Any?>
    ): Call<HalaqahGroupResponse>

    @FormUrlEncoded
    @POST("halaqah/select")
    fun getHalaqahMember(
        @FieldMap params: HashMap<String?, Any?>
    ): Call<HalaqahMemberResponse>

    @FormUrlEncoded
    @POST("halaqah/create")
    fun createHalaqah(
        @FieldMap params: HashMap<String?, Any?>
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("halaqah/join")
    fun joinHalaqah(
        @FieldMap params: HashMap<String?, Any?>
    ): Call<ApiResponse>
}