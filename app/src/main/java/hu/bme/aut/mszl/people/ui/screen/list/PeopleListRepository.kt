package hu.bme.aut.mszl.people.ui.screen.list

import hu.bme.aut.mszl.people.network.RandomUserApi
import hu.bme.aut.mszl.people.persistence.PeopleDao
import hu.bme.aut.mszl.people.persistence.model.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PeopleListRepository @Inject constructor(
    private val randomUserApi: RandomUserApi,
    private val peopleDao: PeopleDao
) {
    suspend fun getPeople() =
        withContext(Dispatchers.IO) {
            randomUserApi.getPeople(50).results
                .map { person -> PersonEntity(
                    categoryId = -1,
                    gender = person.gender,
                    name = person.name.toString(),
                    location = person.location.toString(),
                    email = person.email,
                    dob = person.dob.date,
                    phone = person.phone,
                    picture_url = person.picture.medium,
                    nat = person.nat
                ) }
        }

    suspend fun getCategory(name: String) =
        withContext(Dispatchers.IO) {
            peopleDao.getCategory(name)
        }

    suspend fun getCategoryNames() =
        withContext(Dispatchers.IO) {
            peopleDao.getCategories().map { it.name }
        }
}