package org.sic4change.chuqabp.course.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sic4change.chuqabp.course.model.domain.Models.User

/**
 * DatabaseUser represents a user entity in the database.
 */
@Entity
data class DatabaseUser constructor(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val surnames: String,
    val photo: String)

/**
 * Convert DatabaseUser results to domain object
 */
fun DatabaseUser.asUserDomainModel(): User {
    return User(
        id = this.id,
        email = this.email,
        name = this.name,
        surnames = this.surnames,
        photo = this.photo
    )
}

@Entity
data class Case (
    @PrimaryKey val id: String = "",
    val name: String = "",
    val surnames: String = "",
    val birthdate: String = "",
    val phone: String = "",
    val email: String = "",
    val photo: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0)
