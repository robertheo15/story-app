package com.example.storyappv2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storyappv2.data.RemoteKeys
import com.example.storyappv2.network.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}