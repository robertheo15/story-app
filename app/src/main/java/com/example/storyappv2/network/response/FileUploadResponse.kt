package com.example.storyappv2.network.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)