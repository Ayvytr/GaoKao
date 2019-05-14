package com.ayvytr.commonlibrary.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author admin
 */

@Parcelize
data class AppSubject(
    var id: Int? = null,
    var name: String? = null,
    var desc: String? = null
) : Parcelable