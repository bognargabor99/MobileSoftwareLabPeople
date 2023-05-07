package hu.bme.aut.mszl.people.ui.screen.details

import hu.bme.aut.mszl.people.network.model.Person
import hu.bme.aut.mszl.people.persistence.PeopleDao
import hu.bme.aut.mszl.people.persistence.model.CategoryEntity
import hu.bme.aut.mszl.people.persistence.model.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PeopleDetailsRepository @Inject constructor(
    private val peopleDao: PeopleDao
) {
    suspend fun addCategory(name: String) {
        withContext(Dispatchers.IO) {
            peopleDao.addCategory(CategoryEntity(name = name))
        }
    }

    suspend fun addPersonToCategory(categoryId: Long, person: Person) {
        val personEntity = PersonEntity(
            categoryId = categoryId,
            gender = person.gender,
            name = person.name.toString(),
            location = person.location.toString(),
            email = person.email,
            dob = person.dob.date,
            phone = person.phone,
            picture_url = person.picture.medium,
            nat = person.nat
        )
        withContext(Dispatchers.IO) {
            peopleDao.addPerson(personEntity)
        }
    }

    suspend fun getCategoryNames() =
        withContext(Dispatchers.IO) {
            peopleDao.getCategories().map { it.name }
        }

    suspend fun deletePerson(personId: Long) =
        withContext(Dispatchers.IO) {
            val personToDelete = peopleDao.getPerson(personId)
            peopleDao.deletePerson(personToDelete)
        }

    suspend fun deleteCategory(name: String) =
        withContext(Dispatchers.IO) {
            val category = peopleDao.getCategory(name).category
            val people = peopleDao.getPeople(category.id)
            people.forEach { peopleDao.deletePerson(it) }
            peopleDao.deleteCategory(category)
        }
}