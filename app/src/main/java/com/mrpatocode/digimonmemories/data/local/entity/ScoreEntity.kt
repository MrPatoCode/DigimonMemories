package com.mrpatocode.digimonmemories.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "success")
    val success: Int,
    @ColumnInfo(name = "attempts")
    val attempts: Int,
    @ColumnInfo(name = "errors")
    val errors: Int,
    @ColumnInfo(name = "level")
    val level: Int,
    @ColumnInfo(name = "date")
    val date: Long
)
