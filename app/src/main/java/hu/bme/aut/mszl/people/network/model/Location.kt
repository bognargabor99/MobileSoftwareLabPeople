package hu.bme.aut.mszl.people.network.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: JsonElement
) {
    override fun toString(): String =
        "${street.number} ${street.name}, $city, $country"
}