package com.seif.foody.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seif.foody.MyApplication
import com.seif.foody.data.database.RecipesDatabase
import com.seif.foody.utils.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(MyApplication::class)
object DatabaseModule {

    @Singleton // dentifies a type that the injector only instantiates once. Not inherited.
    @Provides
    fun provideDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}