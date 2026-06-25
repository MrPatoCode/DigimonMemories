package com.mrpatocode.digimonmemories.data.remote.interceptor

import com.mrpatocode.digimonmemories.domain.repository.NetworkConnectivity
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(
    private val networkConnectivity: NetworkConnectivity
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkConnectivity.isConnected()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }
}

class NoConnectivityException(): IOException(){
    override val message: String
        get() = "There is no internet connection"
}