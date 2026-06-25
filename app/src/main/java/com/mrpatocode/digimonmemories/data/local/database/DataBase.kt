package com.mrpatocode.digimonmemories.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrpatocode.digimonmemories.data.local.dao.ScoreDao
import com.mrpatocode.digimonmemories.data.local.entity.ScoreEntity

@Database(
    entities = [ScoreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}