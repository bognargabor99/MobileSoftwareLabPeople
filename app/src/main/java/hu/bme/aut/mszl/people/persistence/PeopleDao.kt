package hu.bme.aut.mszl.people.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hu.bme.aut.mszl.people.persistence.model.CategoryEntity
import hu.bme.aut.mszl.people.persistence.model.CategoryWithPeople
import hu.bme.aut.mszl.people.persistence.model.PersonEntity

@Dao
interface PeopleDao {
    /// People
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getPeople(categoryId: Long): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPerson(person: PersonEntity)

    @Delete
    fun deletePerson(person: PersonEntity)

    /// Categories
    @Query("SELECT * FROM categories")
    fun getCategories(): List<CategoryEntity>

    @Insert
    fun addCategory(category: CategoryEntity)

    @Delete
    fun deleteCategory(category: CategoryEntity)


    /// One-to-many relationship
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithPeople(): List<CategoryWithPeople>
}