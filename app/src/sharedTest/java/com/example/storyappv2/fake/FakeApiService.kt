package com.example.storyappv2.fake

import com.example.storyappv2.network.FileUploadResponse
import com.example.storyappv2.network.RegisterResponse
import com.example.storyappv2.network.StoryResponse
import com.example.storyappv2.network.UserResponse
import com.example.storyappv2.network.api.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class FakeApiService: ApiService {

    private val dummyResponse = DataDummy.generateDummyStoryResponse()

    override fun register(name: String, email: String, password: String): Call<RegisterResponse> {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String): Call<UserResponse> {
        TODO("Not yet implemented")
    }

    override fun uploadImage(
        auth: String,
        description: RequestBody,
        file: MultipartBody.Part
    ): Call<FileUploadResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(auth: String, page: Int, size: Int, location: Int): StoryResponse {
        return dummyResponse
    }
}