package nh.nawafil.hidayatullah.release.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: UserItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class UserItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("halaqah_name")
	val halaqahName: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("halaqah_level")
	val halaqahLevel: String? = null,

	@field:SerializedName("halaqah_id")
	val halaqahId: String? = null,

	@field:SerializedName("halaqah_date_created")
	val halaqahDateCreated: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("halaqah_date_join")
	val halaqahDateJoin: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("date_join")
	val dateJoin: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
