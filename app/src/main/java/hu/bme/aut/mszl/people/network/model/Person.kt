package hu.bme.aut.mszl.people.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val phone: String,
    val picture: Picture,
    val nat: String
) {
    init {
        check((gender == "male") or (gender == "female"))
    }
}

