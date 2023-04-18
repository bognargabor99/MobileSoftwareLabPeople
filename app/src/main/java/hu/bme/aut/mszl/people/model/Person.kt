package hu.bme.aut.mszl.people.model

data class Person(
    val id: Long,
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

