package org.sic4change.chuqabp.domain

class Models {

    /**
     * LoginResponse represent a result login object
     */
    data class LoginResponse(
        var logged: Boolean,
        var error: String)

}