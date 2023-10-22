package nh.nawafil.hidayatullah.release.data.network.response

import com.google.gson.annotations.SerializedName

data class NawafilResponseSingle(

	@field:SerializedName("data")
	val data: NawafilSingleItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class NawafilSingleItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("ba_dzuhur")
	val baDzuhur: String? = null,

	@field:SerializedName("dzikir_sore")
	val dzikirSore: String? = null,

	@field:SerializedName("dzikir_malam")
	val dzikirMalam: String? = null,

	@field:SerializedName("ba_isya")
	val baIsya: String? = null,

	@field:SerializedName("infaq")
	val infaq: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("ket_dakwah")
	val ketDakwah: Any? = null,

	@field:SerializedName("tahajjud")
	val tahajjud: String? = null,

	@field:SerializedName("qo_subuh")
	val qoSubuh: String? = null,

	@field:SerializedName("ba_maghrib")
	val baMaghrib: String? = null,

	@field:SerializedName("quran")
	val quran: String? = null,

	@field:SerializedName("witir")
	val witir: String? = null,

	@field:SerializedName("rakaat_tahajjud")
	val rakaatTahajjud: String? = null,

	@field:SerializedName("dhuha")
	val dhuha: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("dzikir_pagi")
	val dzikirPagi: String? = null,

	@field:SerializedName("qo_dzuhur")
	val qoDzuhur: String? = null,

	@field:SerializedName("dakwah")
	val dakwah: String? = null
)
