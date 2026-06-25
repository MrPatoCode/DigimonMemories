package com.mrpatocode.digimonmemories.domain.model

data class Score(
    val id: Int = 0,
    val success: Int,
    val attempts: Int,
    val errors: Int,
    val level: Int,
    val date: Long = System.currentTimeMillis()
)
