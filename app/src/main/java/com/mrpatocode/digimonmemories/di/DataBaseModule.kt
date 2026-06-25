package com.mrpatocode.digimonmemories.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mrpatocode.digimonmemories.data.local.dao.ScoreDao
import com.mrpatocode.digimonmemories.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database")
            .build()
    }

    @Provides
    fun provideScoreDao(appDatabase: AppDatabase): ScoreDao = appDatabase.scoreDao()

}