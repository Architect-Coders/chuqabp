package org.sic4change.domain

data class Case (
    val id: String = "",
    val person: String = "",
    val name: String = "",
    val surnames: String = "",
    val date: Long = 0,
    val hour: String = "",
    val place: String = "",
    val physic: Boolean = false,
    val sexual: Boolean = false,
    val psychologic: Boolean = false,
    val social: Boolean = false,
    val economic: Boolean = false,
    val description: String = "",
    val resources: String = "",
    val status: String = "",
    val closeDescription: String = "",
    val closeReason: String = "",
    val closeDate: String = "")