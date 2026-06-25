package com.mrpatocode.digimonmemories.data.repository

import com.mrpatocode.digimonmemories.data.remote.api.DigimonApiService
import com.mrpatocode.digimonmemories.data.remote.mapper.toDomain
import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.domain.repository.DigimonRepository
import com.mrpatocode.digimonmemories.domain.repository.NetworkConnectivity
import com.mrpatocode.digimonmemories.util.Resource
import javax.inject.Inject

class DigimonRepositoryImpl @Inject constructor(
    private val digimonApiService: DigimonApiService,
    private val networkConnectivity: NetworkConnectivity
) : DigimonRepository {

    override suspend fun getAllDigimon(): Resource<List<Digimon>> {
        return try {
            if (!networkConnectivity.isConnected())
                return Resource.Error("There is no internet connection")

            val response = digimonApiService.getAllDigimon()
            if (response.isSuccessful) {
                response.body()?.let { list ->
                    Resource.Success(list.map { it.toDomain() })
                } ?: Resource.Error("Empty response")
            } else{
                Resource.Error("Error: ${response.code()}")
            }
        } catch (e: Exception){
            Resource.Error(e.message?: "There is no internet connection")
        } catch (e: Exception){
            Resource.Error(e.message?: "Failed to get data")
        }
    }
}