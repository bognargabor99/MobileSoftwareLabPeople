package hu.bme.aut.mszl.people.network

import hu.bme.aut.mszl.people.network.model.APIResult
import hu.bme.aut.mszl.people.network.model.Person
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RandomUserApi {
    @GET
    suspend fun getPeople(
        @Query("results") results: Int = 50
    ): APIResult

    // ============================
    // || Mocked endpoints below ||
    // || Should not be called!  ||
    // ============================
    @POST
    suspend fun addPerson(
        @Body person: Person
    )

    @GET("{id}")
    suspend fun getPerson(
        @Path("id") id: Long
    ): Person

    @PUT("{id}")
    suspend fun editPerson(
        @Path("id") id: Long,
        @Body person: Person
    ): Person

    @DELETE("{id}")
    suspend fun deletePerson(
        @Path("id") id: Long
    ): Person
}