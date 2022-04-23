package com.example.storyappv2.fake

import com.example.storyappv2.network.RegisterResponse
import com.example.storyappv2.network.Story
import com.example.storyappv2.network.StoryResponse
import com.example.storyappv2.network.User
import com.example.storyappv2.network.UserResponse

object DataDummy {

    fun generateLoginResponseSuccess(): UserResponse =
        UserResponse(
            false,
            "success",
            User(
                "user-1bwsvoNzzBRARkUt",
                "Robert",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTFid3N2b056ekJSQVJrVXQiLCJpYXQiOjE2NTA2MTc3ODd9.t9PJDHx1bu-OwJANnEsxyQl5C0IA4rRf7ejieFpr4Eg",
                true
            )
        )

//    fun generateRegisterResponseFail(): RegisterResponse =

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