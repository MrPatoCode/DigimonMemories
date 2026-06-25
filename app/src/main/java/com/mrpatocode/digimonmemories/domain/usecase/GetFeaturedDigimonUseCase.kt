package com.mrpatocode.digimonmemories.domain.usecase

import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.domain.repository.DigimonRepository
import com.mrpatocode.digimonmemories.util.Resource
import javax.inject.Inject

class GetFeaturedDigimonUseCase @Inject constructor(
    private val digimonRepository: DigimonRepository
) {
    private var cachedDigimon: List<Digimon>? = null

    suspend operator fun invoke(count: Int, forceRefresh: Boolean = false): Resource<List<Digimon>>{
        if (!forceRefresh && cachedDigimon != null){
            return Resource.Success(getRandomFeatured(count))
        }

        return when(val result = digimonRepository.getAllDigimon()){
            is Resource.Success ->{
                val allDigimon = result.data ?: emptyList()

                cachedDigimon = allDigimon

                Resource.Success(getRandomFeatured(count))
            }

            is Resource.Loading -> result
            is Resource.Error -> result
        }
    }

    private fun getRandomFeatured(count: Int): List<Digimon>{
        return cachedDigimon!!.shuffled().take(count)
    }
}