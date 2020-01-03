package org.sic4change.chuqabp.course.data.server

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val results: List<User>)

@JsonClass(generateAdapter = true)
data class User(
    @Exclude val id: String = "",
    @Exclude val email: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val photo: String = "")

@JsonClass(generateAdapter = true)
data class Case (
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val birthdate: String = "",
    @Exclude val phone: String = "",
    @Exclude val email: String = "",
    @Exclude val photo: String = "",
    @Exclude val latitude: Double = 0.0,
    @Exclude val longitude: Double = 0.0)

@JsonClass(generateAdapter = true)
data class NetworkCasesContainer(val results: List<Case>)