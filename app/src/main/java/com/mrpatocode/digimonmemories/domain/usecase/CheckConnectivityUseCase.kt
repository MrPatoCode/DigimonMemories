package com.mrpatocode.digimonmemories.domain.usecase

import com.mrpatocode.digimonmemories.domain.repository.NetworkConnectivity
import javax.inject.Inject

class CheckConnectivityUseCase @Inject constructor(
    private val networkConnectivity: NetworkConnectivity
) {
    operator fun invoke(): Boolean{
        return networkConnectivity.isConnected()
    }
}