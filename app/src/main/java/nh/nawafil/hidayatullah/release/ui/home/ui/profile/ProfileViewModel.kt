package nh.nawafil.hidayatullah.release.ui.home.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences

class ProfileViewModel (private val pref: AppPreferences) : ViewModel() {
    fun logout(){
        viewModelScope.launch {
            pref.clearUserData()
        }
    }
}