package com.mrpatocode.digimonmemories.domain.usecase

import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.domain.repository.ScoreRepository
import com.mrpatocode.digimonmemories.util.Resource
import javax.inject.Inject


class GetAllScoresUseCase @Inject constructor(
    private val scoreRepository: ScoreRepository
) {
    suspend operator fun invoke(): Resource<List<Score>> {
        return when(val result = scoreRepository.getAllScores()){
            is Resource.Success -> {
                val allScores = result.data ?: emptyList()
                Resource.Success(allScores)
            }
            is Resource.Loading -> result
            is Resource.Error -> result

        }
    }
}