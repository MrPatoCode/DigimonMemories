package com.mrpatocode.digimonmemories.di

import android.content.Context
import com.mrpatocode.digimonmemories.data.remote.api.DigimonApiService
import com.mrpatocode.digimonmemories.data.remote.interceptor.ConnectivityInterceptor
import com.mrpatocode.digimonmemories.data.repository.NetworkConnectivityImpl
import com.mrpatocode.digimonmemories.domain.repository.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity{
        return NetworkConnectivityImpl(context)
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(networkConnectivity: NetworkConnectivity): ConnectivityInterceptor{
        return ConnectivityInterceptor(networkConnectivity)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(connectivityInterceptor: ConnectivityInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(connectivityInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://digimon-api.vercel.app/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDigimonApiService(retrofit: Retrofit): DigimonApiService{
        return retrofit.create(DigimonApiService::class.java)
    }
}