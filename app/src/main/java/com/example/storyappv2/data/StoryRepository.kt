package com.example.storyappv2.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.storyappv2.database.StoryDatabase
import com.example.storyappv2.network.FileUploadResponse
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.StoryResponse
import com.example.storyappv2.network.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val storyDatabase: StoryDatabase?,
    private val apiService: ApiService
) {

    fun findAllStories(token: String): Flow<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = storyDatabase?.let { StoryRemoteMediator(it, apiService, token) },
            pagingSourceFactory = {
                storyDatabase?.storyDao()!!.getAllStory()
            }
        ).flow
    }

    suspend fun getData(): List<Story> {
        return storyDatabase?.storyDao()!!.findAll()
    }

    suspend fun createImage(
        token: String,
        imageMultipart: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): Flow<Result<FileUploadResponse>> = flow {
        try {
            val response = apiService.uploadImage(token, imageMultipart, description, lat, lon)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }
}