package com.mrpatocode.digimonmemories.data.mapper

import com.mrpatocode.digimonmemories.data.local.entity.ScoreEntity
import com.mrpatocode.digimonmemories.domain.model.Score

fun ScoreEntity.toDomain(): Score {
    return Score(
        id = id,
        success = success,
        attempts = attempts,
        errors = errors,
        level = level,
        date = date
    )
}

fun Score.toEntity(): ScoreEntity {
    return ScoreEntity(
        id = id,
        success = success,
        attempts = attempts,
        errors = errors,
        level = level,
        date = date
    )
}