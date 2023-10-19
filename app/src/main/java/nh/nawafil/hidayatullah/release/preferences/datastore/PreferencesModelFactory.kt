package nh.nawafil.hidayatullah.release.preferences.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import nh.nawafil.hidayatullah.release.ui.home.ui.profile.ProfileViewModel
import nh.nawafil.hidayatullah.release.ui.login.LoginViewModel
import nh.nawafil.hidayatullah.release.ui.splash.MainViewModel

@Suppress("UNCHECKED_CAST")
class PreferencesModelFactory(private val pref: AppPreferences) : NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}