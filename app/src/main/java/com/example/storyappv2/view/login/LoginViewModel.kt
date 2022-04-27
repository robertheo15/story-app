package com.example.storyappv2.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappv2.data.UserRepository
import com.example.storyappv2.network.response.User
import com.example.storyappv2.network.response.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun setUser(user: User) {
        viewModelScope.launch {
            userRepository.setUser(user)
        }
    }

    suspend fun login(email: String, password: String): Flow<Result<UserResponse>> =
        userRepository.login(email, password)

    fun getUser(): Flow<User?> = userRepository.getUser()

    suspend fun logout() {
        userRepository.logout()
    }
}