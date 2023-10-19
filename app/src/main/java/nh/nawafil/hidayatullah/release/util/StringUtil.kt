package nh.nawafil.hidayatullah.release.util

import android.text.TextUtils

fun String.isEmailValid(): Boolean  {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.timeStamptoString(): String = substring(0, 10)