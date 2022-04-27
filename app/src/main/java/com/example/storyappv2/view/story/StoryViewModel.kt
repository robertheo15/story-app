package com.example.storyappv2.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyappv2.data.StoryRepository
import com.example.storyappv2.network.response.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
) :
    ViewModel() {

    fun findAllStories (token: String) : LiveData<PagingData<Story>>{
        return storyRepository.findAllStories("Bearer $token").cachedIn(viewModelScope).asLiveData()
    }

    suspend fun getData(): List<Story> {
        return storyRepository.getData()
    }
}