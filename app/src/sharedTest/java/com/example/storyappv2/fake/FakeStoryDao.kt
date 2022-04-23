package com.example.storyappv2.fake

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.example.storyappv2.database.StoryDao
import com.example.storyappv2.database.StoryDatabase
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.api.ApiService

class FakeStoryDao(
    private val storyDatabase: StoryDatabase?,
    private val apiService: ApiService
): StoryDao {

    private var storyData = mutableListOf<List<Story>>()

    override suspend fun insertStory(story: List<Story>) {
        storyData.add(story)
    }

    override fun getAllStory(): PagingSource<Int, Story> {
//        val observableNews = MutableLiveData<List<Story>>()
//        observableNews.value = storyData
//        return observableNews
        TODO("Not yet implemented")
    }

    override suspend fun findAll(): List<Story> {
        return storyDatabase?.storyDao()!!.findAll()

    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}