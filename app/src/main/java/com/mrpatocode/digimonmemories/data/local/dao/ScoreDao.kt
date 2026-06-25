package com.mrpatocode.digimonmemories.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrpatocode.digimonmemories.data.local.entity.ScoreEntity

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    @Query("SELECT * FROM score ORDER BY id DESC")
    suspend fun getAllScores(): List<ScoreEntity>

    @Delete
    suspend fun deleteScore(score: ScoreEntity)
}