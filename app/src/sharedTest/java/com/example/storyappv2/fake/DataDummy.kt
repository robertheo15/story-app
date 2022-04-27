package com.example.storyappv2.fake

import com.example.storyappv2.network.RegisterResponse
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.StoryResponse
import com.example.storyappv2.network.User
import com.example.storyappv2.network.UserResponse

object DataDummy {

    fun generateLoginResponseSuccess(): UserResponse {
        return UserResponse(
            false,
            "success",
            User(
                "user-yj5pc_LARC_AgK61",
                "Arif Faizin",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flEMaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I",
                true
            )
        )
    }

    fun generateRegisterResponseFail(): RegisterResponse = RegisterResponse(false, "User Created")

    fun generateDummyStory(): List<Story> {
        val storyList = ArrayList<Story>()
        for (i in 0..10) {
            val story = Story(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "Lorem Ipsum",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                -10.212,
                -16.002,
            )
            storyList.add(story)
        }
        return storyList
    }

    fun generateDummyStoryResponse(): StoryResponse {
        val storyList = ArrayList<Story>()
        for (i in 0..10) {
            val story = Story(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "Lorem Ipsum",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                -10.212,
                -16.002,
            )
            storyList.add(story)
        }
        return StoryResponse(false,"Stories fetched successfully", storyList)
    }
}