package hu.bme.aut.mszl.people.model

data class Category(
    val id: Long,
    val name: String,
    val people: List<Person>
)