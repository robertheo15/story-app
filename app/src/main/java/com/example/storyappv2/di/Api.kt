package com.example.storyappv2.di

import com.example.storyappv2.network.api.ApiConfig
import com.example.storyappv2.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Api {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.getApiService()
}