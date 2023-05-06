package hu.bme.aut.mszl.people.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Name(val first: String, val last: String)

