package com.example.storyappv2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyappv2.database.StoryDatabase
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.api.ApiService

class StoryRepository(
    private val storyDatabase: StoryDatabase?,
    private val apiService: ApiService
) {

    fun findAll(token: String): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = storyDatabase?.let { StoryRemoteMediator(it, apiService, token) },
            pagingSourceFactory = {
                storyDatabase?.storyDao()!!.getAllStory()
            }
        ).liveData
    }

    suspend fun getData(): List<Story> {
        return storyDatabase?.storyDao()!!.findAll()
    }
}