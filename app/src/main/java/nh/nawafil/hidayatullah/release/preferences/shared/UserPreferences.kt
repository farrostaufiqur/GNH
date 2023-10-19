package nh.nawafil.hidayatullah.release

import android.content.Context
import nh.nawafil.hidayatullah.release.preferences.shared.PreferencesModel

internal class UserPreferences(context: Context) {
    companion object {
        private const val PREF_NAME = "gerakan.nawafil.gnh.userdata"
        private const val ID_USER = "gerakan.nawafil.gnh.id.user"
        private const val TOKEN_USER = "gerakan.nawafil.gnh.token user"
        private const val NAME_USER = "gerakan.nawafil.gnh.name.user"
        private const val EMAIL_USER = "gerakan.nawafil.gnh.email.user"
        private const val ID_HALAQAH = "gerakan.nawafil.gnh.id.halaqah"
    }
    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUser(value: PreferencesModel) {
        val editor = preferences.edit()
        editor.putString(ID_USER, value.id).apply()
        editor.putString(TOKEN_USER, value.token).apply()
        editor.putString(NAME_USER, value.name).apply()
        editor.putString(EMAIL_USER, value.email).apply()
        editor.putString(ID_HALAQAH, value.id_halaqah).apply()
        editor.apply()
    }
    fun getUser(): PreferencesModel {
        val model = PreferencesModel()
        model.id = preferences.getString(ID_USER, "")
        model.token = preferences.getString(TOKEN_USER, "")
        model.name = preferences.getString(NAME_USER, "")
        model.email = preferences.getString(EMAIL_USER, "")
        model.id_halaqah = preferences.getString(ID_HALAQAH, "")
        return model
    }
}