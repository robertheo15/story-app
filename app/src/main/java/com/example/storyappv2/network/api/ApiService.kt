package com.example.storyappv2.network.api

import com.example.storyappv2.network.FileUploadResponse
import com.example.storyappv2.network.RegisterResponse
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.StoryResponse
import com.example.storyappv2.network.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") auth: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<FileUploadResponse>

//    @GET("stories")
//    fun findAll(
//        @Header("Authorization") auth: String,
//    ): Call<StoryResponse>

    @GET("stories")
    suspend fun findAll(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int = 1,
    ): StoryResponse
}