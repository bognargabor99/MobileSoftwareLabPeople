package hu.bme.aut.mszl.people.model

data class Person(
    val id: Long,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val phone: String,
    val Picture: Picture,
    val nat: String
)

