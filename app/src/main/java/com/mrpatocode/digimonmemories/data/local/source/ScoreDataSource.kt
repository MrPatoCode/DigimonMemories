package com.mrpatocode.digimonmemories.data.local.source

import com.mrpatocode.digimonmemories.data.local.dao.ScoreDao
import com.mrpatocode.digimonmemories.data.local.entity.ScoreEntity
import javax.inject.Inject

class ScoreDataSource @Inject constructor(
    private val scoreDao: ScoreDao
) {
    suspend fun insertScore(score: ScoreEntity) {
        scoreDao.insertScore(score)
    }

    suspend fun getAllScores(): List<ScoreEntity> {
        return scoreDao.getAllScores()
    }

    suspend fun deleteScore(score: ScoreEntity) {
        scoreDao.deleteScore(score)
    }
}
