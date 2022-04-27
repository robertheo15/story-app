package com.example.storyappv2.view.story.create

import androidx.lifecycle.ViewModel
import com.example.storyappv2.data.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel() {

    suspend fun createImage(
        token: String,
        imageMultipart: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ) = storyRepository.createImage("Bearer $token", imageMultipart, description, lat, lon)
}