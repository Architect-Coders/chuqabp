package org.sic4change.chuqabp.network

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass
import org.sic4change.chuqabp.database.DatabaseUser


/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 * @see domain package for
 */

/**
 * VideoHolder holds a list of User.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "resultados": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val resultados: List<User>)


/**
 * User represent a user object
 */
@JsonClass(generateAdapter = true)
data class User(
    @Exclude val id: String = "",
    @Exclude val email: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val photo: String = "")

/**
 * Convert NetworkUser results to database objects
 */
fun User.asDatabaseModel(): DatabaseUser  {
    return DatabaseUser(
        id = this.id,
        email = this.email,
        name = this.name,
        surnames = this.surnames,
        photo = this.photo
    )
}