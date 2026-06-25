package com.mrpatocode.digimonmemories.data.repository

import com.mrpatocode.digimonmemories.data.local.source.ScoreDataSource
import com.mrpatocode.digimonmemories.data.mapper.toDomain
import com.mrpatocode.digimonmemories.data.mapper.toEntity
import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.domain.repository.ScoreRepository
import com.mrpatocode.digimonmemories.util.Resource
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(
    private val scoreDataSource: ScoreDataSource
) : ScoreRepository {
    override suspend fun getAllScores(): Resource<List<Score>> {
        return try {
            val scores = scoreDataSource.getAllScores()
            Resource.Success(scores.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to get scores")
        }
    }

    override suspend fun insertScore(score: Score): Resource<Unit> {
        return try {
            scoreDataSource.insertScore(score.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to insert score")
        }
    }

    override suspend fun deleteScore(score: Score): Resource<Unit> {
        return try {
            scoreDataSource.deleteScore(score.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to delete score")
        }
    }

}