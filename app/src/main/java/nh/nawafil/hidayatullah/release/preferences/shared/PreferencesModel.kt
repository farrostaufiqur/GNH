package nh.nawafil.hidayatullah.release.preferences.shared

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreferencesModel (
    var id: String? = null,
    var token: String? = null,
    var name: String? = null,
    var username: String? = null,
    var level: String? = null,
    var image: String? = null,
    var dateJoin: String? = null,
    var halaqahId: String? = null,
    var halaqahName: String? = null,
    var halaqahLevel: String? = null,
    var halaqahDateCreated: String? = null,
    var halaqahDateJoin: String? = null,
) : Parcelable