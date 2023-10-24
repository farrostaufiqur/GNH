package nh.nawafil.hidayatullah.release

import android.content.Context
import nh.nawafil.hidayatullah.release.preferences.shared.PreferencesModel

internal class UserPreferences(context: Context) {
    companion object {
        private const val PREF_NAME = "gerakan.nawafil.gnh.userdata"
        private const val ID_USER = "gerakan.nawafil.gnh.id.user"
        private const val TOKEN_USER = "gerakan.nawafil.gnh.token user"
        private const val NAME_USER = "gerakan.nawafil.gnh.name.user"
        private const val USERNAME_USER = "gerakan.nawafil.gnh.username.user"
        private const val LEVEL_USER = "gerakan.nawafil.gnh.level.user"
        private const val IMAGE_USER = "gerakan.nawafil.gnh.image.user"
        private const val DATE_JOIN_USER = "gerakan.nawafil.gnh.date.join.user"
        private const val HALAQAH_ID_USER = "gerakan.nawafil.gnh.halaqah.id.user"
        private const val HALAQAH_NAME_USER = "gerakan.nawafil.gnh.halaqah.name.user"
        private const val HALAQAH_LEVEL_USER = "gerakan.nawafil.gnh.halaqah.level.user"
        private const val HALAQAH_DATE_CREATED_USER = "gerakan.nawafil.gnh.halaqah.date.created.user"
        private const val HALAQAH_DATE_JOIN_HALAQAH = "gerakan.nawafil.gnh.halaqah.date.join.halaqah"

        private const val MEMBER_HALAQAH_ID = "gerakan.nawafil.gnh.halaqah.member.id"
        private const val MEMBER_HALAQAH_TOKEN = "gerakan.nawafil.gnh.halaqah.member.token"
    }
    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUser(value: PreferencesModel) {
        val editor = preferences.edit()
        val (id, token, name, username, level, image, dateJoin, halaqahId, halaqahName, halaqahLevel,
            halaqahDateCreated, halaqahDateJoin) = value
        editor.putString(ID_USER, id).apply()
        editor.putString(TOKEN_USER, token).apply()
        editor.putString(NAME_USER, name).apply()
        editor.putString(USERNAME_USER, username).apply()
        editor.putString(LEVEL_USER, level).apply()
        editor.putString(IMAGE_USER, image).apply()
        editor.putString(DATE_JOIN_USER, dateJoin).apply()
        editor.putString(HALAQAH_ID_USER, halaqahId).apply()
        editor.putString(HALAQAH_NAME_USER, halaqahName).apply()
        editor.putString(HALAQAH_LEVEL_USER, halaqahLevel).apply()
        editor.putString(HALAQAH_DATE_CREATED_USER, halaqahDateCreated).apply()
        editor.putString(HALAQAH_DATE_JOIN_HALAQAH, halaqahDateJoin).apply()
        editor.apply()
    }
    fun getUser(): PreferencesModel {
        val model = PreferencesModel()
        model.id = preferences.getString(ID_USER, "")
        model.token = preferences.getString(TOKEN_USER, "")
        model.name = preferences.getString(NAME_USER, "")
        model.username = preferences.getString(USERNAME_USER, "")
        model.level = preferences.getString(LEVEL_USER, "")
        model.image = preferences.getString(IMAGE_USER, "")
        model.dateJoin = preferences.getString(DATE_JOIN_USER, "")
        model.halaqahId = preferences.getString(HALAQAH_ID_USER, "")
        model.halaqahName = preferences.getString(HALAQAH_NAME_USER, "")
        model.halaqahLevel = preferences.getString(HALAQAH_LEVEL_USER, "")
        model.halaqahDateCreated = preferences.getString(HALAQAH_DATE_CREATED_USER, "")
        model.halaqahDateJoin = preferences.getString(HALAQAH_DATE_JOIN_HALAQAH, "")
        return model
    }

    fun setMemberHalaqah(id: String, token: String){
        val editor = preferences.edit()
        editor.putString(MEMBER_HALAQAH_ID, id).apply()
        editor.putString(MEMBER_HALAQAH_TOKEN, token).apply()
        editor.apply()
    }

    fun getMemberHalaqah(): Pair<String, String>{
        val id = preferences.getString(MEMBER_HALAQAH_ID, "")
        val token = preferences.getString(MEMBER_HALAQAH_TOKEN, "")
        return Pair(id!!, token!!)
    }
}