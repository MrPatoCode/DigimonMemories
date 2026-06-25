package com.mrpatocode.digimonmemories.domain.repository

import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.util.Resource


interface ScoreRepository {
    suspend fun getAllScores(): Resource<List<Score>>
    suspend fun insertScore(score: Score): Resource<Unit>
    suspend fun deleteScore(score: Score): Resource<Unit>

}