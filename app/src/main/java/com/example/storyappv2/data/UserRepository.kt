package com.example.storyappv2.data

import com.example.storyappv2.network.response.RegisterResponse
import com.example.storyappv2.network.response.User
import com.example.storyappv2.network.response.UserResponse
import com.example.storyappv2.network.api.ApiService
import com.example.storyappv2.utils.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val pref: UserPreference
) {

    suspend fun login(email: String, password: String): Flow<Result<UserResponse>> = flow {
        try {
            val response = apiService.login(email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    suspend fun logout(){
        pref.logout()
    }

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> = flow {
        try {
            val response = apiService.register(name, email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    suspend fun setUser(user: User) {
        user.isLogin = true
        pref.setUser(user)
    }

    fun getUser(): Flow<User?> = pref.getUser()
}