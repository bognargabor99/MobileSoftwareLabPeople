package hu.bme.aut.mszl.people.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var categoryId: Long,
    val gender: String,
    val name: String,
    val location: String,
    val email: String,
    val dob: String,
    val phone: String,
    val picture_url: String,
    val nat: String
)