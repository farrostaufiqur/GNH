package nh.nawafil.hidayatullah.release.data.network.response

import com.google.gson.annotations.SerializedName

data class HalaqahMemberResponse(

	@field:SerializedName("data")
	val data: List<HalaqahMemberItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class HalaqahMemberItem(

	@field:SerializedName("no")
	val no: Int? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("half")
	val half: Int? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("yes")
	val yes: Int? = null,

	@field:SerializedName("halaqah_level")
	val halaqahLevel: String? = null,

	@field:SerializedName("halaqah_id")
	val halaqahId: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("halaqah_date_join")
	val halaqahDateJoin: String? = null,

	@field:SerializedName("permit")
	val permit: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("date_join")
	val dateJoin: String? = null
)
