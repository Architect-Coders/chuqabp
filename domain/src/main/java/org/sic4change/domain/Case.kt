package org.sic4change.domain

data class Case (
    val id: String = "",
    val person: String = "",
    val date: String = "",
    val hour: String = "",
    val place: String = "",
    val physic: Boolean = false,
    val sexual: Boolean = false,
    val psychologic: Boolean = false,
    val social: Boolean = false,
    val economic: Boolean = false,
    val description: String = "")