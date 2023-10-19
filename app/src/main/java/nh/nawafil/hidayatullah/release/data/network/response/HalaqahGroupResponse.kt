package nh.nawafil.hidayatullah.release.data.network.response

import com.google.gson.annotations.SerializedName

data class HalaqahGroupResponse(

	@field:SerializedName("data")
	val data: HalaqahGroupItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class HalaqahGroupItem(
	@field:SerializedName("date_created")
	val dateCreated: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
