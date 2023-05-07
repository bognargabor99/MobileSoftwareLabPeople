package hu.bme.aut.mszl.people.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Dob(
    val date: String
)