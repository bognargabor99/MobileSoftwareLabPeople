package hu.bme.aut.mszl.people.model

data class Location(
    val id: Long,
    val street: Street,
    val city: String,
    val state: String,
    val county: String,
    val postcode: String,
)