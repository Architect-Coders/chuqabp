package org.sic4change.chuqabp.domain

class Models {

    /**
     * LoginResponse represent a result login object
     */
    data class LoginResponse(
        var logged: Boolean,
        var email: String,
        var error: String)


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