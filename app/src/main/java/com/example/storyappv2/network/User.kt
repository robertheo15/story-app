package com.example.storyappv2.network

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("userId")
    var userId: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("token")
    var token: String,

    var isLogin: Boolean
)