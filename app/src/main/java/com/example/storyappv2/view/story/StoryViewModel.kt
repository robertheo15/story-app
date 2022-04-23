package com.example.storyappv2.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyappv2.data.StoryRepository
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.User
import com.example.storyappv2.utils.UserPreference
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository, private val pref: UserPreference) :
    ViewModel() {

    val story: (String) -> LiveData<PagingData<Story>> = {
            token: String ->
        storyRepository.findAll("Bearer $token").cachedIn(viewModelScope)
    }

    suspend fun getData(): List<Story> {
        return storyRepository.getData()
    }


    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    companion object {
        private const val TAG = "StoryViewModel"
    }
}