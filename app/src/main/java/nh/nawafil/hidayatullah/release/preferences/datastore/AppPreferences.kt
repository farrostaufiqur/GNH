package nh.nawafil.hidayatullah.release.preferences.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nh.nawafil.hidayatullah.release.Constant.ID_KEY
import nh.nawafil.hidayatullah.release.Constant.LANG_KEY
import nh.nawafil.hidayatullah.release.Constant.THEME_KEY
import nh.nawafil.hidayatullah.release.Constant.TOKEN_KEY

class AppPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveTheme(theme: Boolean) {
        dataStore.edit {
            it[THEME] = theme
        }
    }

    fun getTheme(): Flow<Boolean?> {
        return dataStore.data.map {
            it[THEME]
        }
    }

    suspend fun saveUserId(id: String) {
        dataStore.edit {
            it[ID] = id
        }
    }

    fun getUserId(): Flow<String?> {
        return dataStore.data.map {
            it[ID]
        }
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit {
            it[TOKEN] = token
        }
    }

    fun getUserToken(): Flow<String?> {
        return dataStore.data.map {
            it[TOKEN]
        }
    }

    suspend fun saveUserLang(lang: String) {
        dataStore.edit {
            it[LANG] = lang
        }
    }

    fun getUserLang(): Flow<String?> {
        return dataStore.data.map {
            it[LANG]
        }
    }

    suspend fun clearUserData() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val THEME = booleanPreferencesKey(THEME_KEY)
        private val ID = stringPreferencesKey(ID_KEY)
        private val TOKEN = stringPreferencesKey(TOKEN_KEY)
        private val LANG = stringPreferencesKey(LANG_KEY)

        @Volatile
        private var INSTANCE: AppPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AppPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}