package nh.nawafil.hidayatullah.release.data.network.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)
