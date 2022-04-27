package com.example.storyappv2.view.signup

import androidx.lifecycle.ViewModel
import com.example.storyappv2.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    suspend fun signUp(name: String, email: String, password: String) =
        userRepository.register(name, email, password)
}