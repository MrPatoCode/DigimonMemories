package com.mrpatocode.digimonmemories.domain.usecase

import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.domain.repository.ScoreRepository
import com.mrpatocode.digimonmemories.util.Resource
import javax.inject.Inject

class InsertScoreGetUseCase @Inject constructor(
    private val scoreRepository: ScoreRepository
) {
    suspend operator fun invoke(score: Score): Resource<Unit> {
        return scoreRepository.insertScore(score)
    }
}