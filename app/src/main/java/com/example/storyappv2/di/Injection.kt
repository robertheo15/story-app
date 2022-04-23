package com.example.storyappv2.di

import android.content.Context
import com.example.storyappv2.network.api.ApiConfig
import com.example.storyappv2.data.StoryRepository
import com.example.storyappv2.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context?): StoryRepository {
        val database = context?.let { StoryDatabase.getDatabase(it) }
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}