package com.example.storyappv2.network

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("error")
    var error: Boolean,

    @field:SerializedName("message")
    var message: String,

    @field:SerializedName("loginResult")
    var loginResult: User?
)