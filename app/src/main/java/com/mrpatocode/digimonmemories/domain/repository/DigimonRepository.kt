package com.mrpatocode.digimonmemories.domain.repository

import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.util.Resource

interface DigimonRepository {
    suspend fun getAllDigimon(): Resource<List<Digimon>>
}