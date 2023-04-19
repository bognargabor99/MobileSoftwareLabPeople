package hu.bme.aut.mszl.people.persistence

import hu.bme.aut.mszl.people.model.Category
import hu.bme.aut.mszl.people.model.Person

interface PeopleDao {
    fun getCategories(): List<Category>

    fun getPeople(categoryId: Long): List<Person>

    fun savePerson(person: Person, categoryId: Long)

    fun deletePerson()
}