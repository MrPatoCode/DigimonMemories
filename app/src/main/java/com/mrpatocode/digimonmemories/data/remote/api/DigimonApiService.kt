package com.mrpatocode.digimonmemories.data.remote.api

import com.mrpatocode.digimonmemories.data.remote.dto.DigimonResponseDtoItem
import retrofit2.Response
import retrofit2.http.GET

interface DigimonApiService {
    @GET("digimon")
    suspend fun getAllDigimon(): Response<List<DigimonResponseDtoItem>>
}