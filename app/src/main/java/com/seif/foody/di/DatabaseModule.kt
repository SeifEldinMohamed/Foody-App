package com.seif.foody.di

import android.content.Context
import androidx.room.Room
import com.seif.foody.data.database.RecipesDatabase
import com.seif.foody.utils.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton // dentifies a type that the injector only instantiates once. Not inherited.
    @Provides
    fun provideDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}

// If you don’t want to provide migrations and you specifically want
// your database to be cleared when you upgrade the version,
// call fallbackToDestructiveMigration in the database builder