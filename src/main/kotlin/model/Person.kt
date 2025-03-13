package org.keane.model

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Int,
    val name: String,
    val age: Int,
    val email: String,
    val phone: String,
    val address: String
)
