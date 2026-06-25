package com.mrpatocode.digimonmemories.di

import com.mrpatocode.digimonmemories.data.repository.DigimonRepositoryImpl
import com.mrpatocode.digimonmemories.data.repository.ScoreRepositoryImpl
import com.mrpatocode.digimonmemories.domain.repository.DigimonRepository
import com.mrpatocode.digimonmemories.domain.repository.ScoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDigimonRepository(digimonRepositoryImpl: DigimonRepositoryImpl): DigimonRepository

    @Binds
    @Singleton
    abstract fun bindScoreRepository(scoreRepositoryImpl: ScoreRepositoryImpl): ScoreRepository
}