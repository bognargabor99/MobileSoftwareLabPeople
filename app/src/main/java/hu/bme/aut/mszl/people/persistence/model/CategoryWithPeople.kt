package hu.bme.aut.mszl.people.persistence.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithPeople(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val people: List<PersonEntity>
)