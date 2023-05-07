package hu.bme.aut.mszl.people.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.mszl.people.persistence.PeopleDao
import hu.bme.aut.mszl.people.persistence.PeopleDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun providePeopleDatabase(@ApplicationContext applicationContext: Context): PeopleDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PeopleDatabase::class.java,
            "people"
        ).build()
    }

    @Provides
    @Singleton
    fun provideReviewDao(PeopleDatabase: PeopleDatabase): PeopleDao {
        return PeopleDatabase.peopleDao()
    }
}