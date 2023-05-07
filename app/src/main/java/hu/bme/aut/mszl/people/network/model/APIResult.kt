package hu.bme.aut.mszl.people.network.model

import kotlinx.serialization.Serializable

@Serializable
data class APIResult(
    val results: List<Person>
)