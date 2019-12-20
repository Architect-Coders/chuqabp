package org.sic4change.chuqabp.course.model.domain

class Models {


    data class UserManagementResponse(
        var successful: Boolean,
        val email: String,
        val error: String)

    data class User(
        val id: String,
        val email: String,
        val name: String,
        val surnames: String,
        val photo: String)

}