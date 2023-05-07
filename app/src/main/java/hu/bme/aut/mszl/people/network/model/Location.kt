package hu.bme.aut.mszl.people.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String) {
    override fun toString(): String =
        "${street.number} ${street.name}, $postcode $city, $state, $country"
}