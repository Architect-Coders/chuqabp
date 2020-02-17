package org.sic4change.chuqabp.course.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User constructor(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val surnames: String,
    val photo: String)


@Entity
data class Person (
    @PrimaryKey val id: String = "",
    val name: String = "",
    val surnames: String = "",
    val birthday: String = "",
    val phone: String = "",
    val email: String = "",
    val photo: String = "",
    val location: String = "")
