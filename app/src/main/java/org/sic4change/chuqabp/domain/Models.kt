package org.sic4change.chuqabp.domain

class Models {

    /**
     * UserManagementResponse represent a result login object
     */
    data class UserManagementResponse(
        var successful: Boolean,
        val email: String,
        val error: String)

    /**
     * User represent a user object
     */
    data class User(
        val id: String,
        val email: String,
        val name: String,
        val surnames: String,
        val photo: String)

}