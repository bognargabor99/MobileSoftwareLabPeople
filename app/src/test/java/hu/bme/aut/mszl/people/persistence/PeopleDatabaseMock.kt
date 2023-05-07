package hu.bme.aut.mszl.people.persistence

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class PeopleDatabaseMock {
    lateinit var database: PeopleDatabase
    lateinit var peopleDao: PeopleDao

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            PeopleDatabase::class.java
        ).build()
        peopleDao = database.peopleDao()
    }

    @After
    fun destroyDatabase() {
        database.close()
    }
}