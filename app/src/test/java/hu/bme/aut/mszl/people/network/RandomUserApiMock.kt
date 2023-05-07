package hu.bme.aut.mszl.people.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import retrofit2.Retrofit
import java.io.File

@RunWith(AndroidJUnit4::class)
abstract class RandomUserApiMock {
    lateinit var server: MockWebServer
    lateinit var randomUserApi: RandomUserApi

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun initServerAndApi() {
        server = MockWebServer()
        randomUserApi = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RandomUserApi::class.java)
    }

    fun queueResponse(filePath: String) {
        val file = File(filePath)
        val reader = file.bufferedReader(charset = Charsets.UTF_8)
        val response = MockResponse()
        response.setBody(reader.readText())
        server.enqueue(response)
    }

    @After
    fun destroyServerAndApi() {
        server.shutdown()
    }
}