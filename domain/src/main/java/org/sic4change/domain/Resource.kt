package org.sic4change.domain

data class Resource (
    val id: String = "",
    val name: String = "",
    val place: String = "",
    val physic: Boolean = false,
    val sexual: Boolean = false,
    val psychologic: Boolean = false,
    val social: Boolean = false,
    val economic: Boolean = false,
    val description: String = "")