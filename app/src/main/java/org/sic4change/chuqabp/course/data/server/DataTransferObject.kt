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
data class Person (
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val birthday: String = "",
    @Exclude val mentorId: String? = "",
    @Exclude val phone: String = "",
    @Exclude val email: String = "",
    @Exclude val photo: String = "",
    @Exclude val location: String = "")

@JsonClass(generateAdapter = true)
data class Case (
    @Exclude val id: String = "",
    @Exclude val person: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val mentorId: String? = "",
    @Exclude val date: String = "",
    @Exclude val hour: String = "",
    @Exclude val place: String = "",
    @Exclude val physic: Boolean = false,
    @Exclude val sexual: Boolean = false,
    @Exclude val psychologic: Boolean = false,
    @Exclude val social: Boolean = false,
    @Exclude val economic: Boolean = false,
    @Exclude val description: String = "")

@JsonClass(generateAdapter = true)
data class Resource (
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val place: String = "",
    @Exclude val physic: Boolean = false,
    @Exclude val sexual: Boolean = false,
    @Exclude val psychologic: Boolean = false,
    @Exclude val social: Boolean = false,
    @Exclude val economic: Boolean = false,
    @Exclude val description: String = "")

@JsonClass(generateAdapter = true)
data class NetworkPersonsContainer(val results: List<Person>)

@JsonClass(generateAdapter = true)
data class NetworkCasesContainer(val results: List<Case>)

@JsonClass(generateAdapter = true)
data class NetworkResourcesContainer(val results: List<Resource>)