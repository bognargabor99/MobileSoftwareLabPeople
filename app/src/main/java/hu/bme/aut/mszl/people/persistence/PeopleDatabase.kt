package hu.bme.aut.mszl.people.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.mszl.people.persistence.model.CategoryEntity
import hu.bme.aut.mszl.people.persistence.model.PersonEntity

@Database(entities = [PersonEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}