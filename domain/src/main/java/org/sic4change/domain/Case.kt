package org.sic4change.domain

data class Case (
    val id: String = "",
    val name: String = "",
    val surnames: String = "",
    val birthdate: String = "",
    val phone: String = "",
    val email: String = "",
    val photo: String = "",
    val location: String)