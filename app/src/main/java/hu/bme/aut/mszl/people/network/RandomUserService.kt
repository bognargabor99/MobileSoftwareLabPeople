package hu.bme.aut.mszl.people.network

import hu.bme.aut.mszl.people.model.Person

interface RandomUserService {
    suspend fun getUsers(): List<Person>
}