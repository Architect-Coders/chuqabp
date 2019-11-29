package org.sic4change.chuqabp.course.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Case (
    val id: String = "",
    val name: String = "",
    val surnames: String = "",
    val birthdate: String = "",
    val phone: String = "",
    val email: String = "",
    val photo: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0) : Parcelable

