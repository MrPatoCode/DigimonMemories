package com.mrpatocode.digimonmemories.domain.usecase

import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.domain.repository.DigimonRepository
import com.mrpatocode.digimonmemories.util.Resource
import javax.inject.Inject

class GetAllDigimonUseCase @Inject constructor(
    private val digimonRepository: DigimonRepository
) {
    suspend operator fun invoke(): Resource<List<Digimon>>{
        return digimonRepository.getAllDigimon()
    }
}