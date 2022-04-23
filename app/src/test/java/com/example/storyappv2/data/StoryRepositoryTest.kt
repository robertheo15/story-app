package com.example.storyappv2.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.storyappv2.MainCoroutineRule
import com.example.storyappv2.database.StoryDao
import com.example.storyappv2.database.StoryDatabase
import com.example.storyappv2.fake.FakeApiService
import com.example.storyappv2.fake.FakeStoryDao
import com.example.storyappv2.network.api.ApiService
import com.example.storyappv2.fake.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class StoryRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var apiService: ApiService
    private lateinit var storyDao: StoryDao
    private lateinit var storyRepository: StoryRepository
    private lateinit var database: StoryDatabase

    @Before
    fun setUp() {
        apiService = FakeApiService()
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StoryDatabase::class.java
        ).build()
//        database = StoryDatabase.getDatabase(InstrumentationRegistry.getInstrumentation().context)
        storyDao = FakeStoryDao(database, apiService)
        storyRepository = StoryRepository(database, apiService)
    }

    @Test
    fun `when getStories Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val expectedStory = DataDummy.generateDummyStoryResponse()
        val actualStory = apiService.findAll(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTFid3N2b056ekJSQVJrVXQiLCJpYXQiOjE2NTA3MTI0NTl9.MCAjmy2yyE3Y_NPtfRNum-cgems4fGT6q50GQyNOmpo",
            5,
            5
        )
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.stories.size, actualStory.stories.size)
    }

//    @Test
//    fun `when deleteStory Should Not Exist in findAllStory`() = runBlocking {
//        val sampleStory = DataDummy.generateDummyStoryResponse()[0]
//        storyDao.sampleStory(sampleNews)
//        storyDao.sampleStory(sampleNews.title)
//        val actualStory = storyDao.getBookmarkedNews().getOrAwaitValue()
//        Assert.assertFalse(actualStory.contains(sampleStory))
//        Assert.assertFalse(storyDao.isNewsBookmarked(sampleStory.title).getOrAwaitValue())
//    }

//    @Test
//    fun `when saveNews Should Exist in getBookmarkedNews`() = mainCoroutineRule.runBlockingTest {
//        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
//        storyDao.saveNews(sampleNews)
//        val actualNews = storyDao.getBookmarkedNews().getOrAwaitValue()
//        assertTrue(actualNews.contains(sampleNews))
//        assertTrue(storyDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
//    }
//
//    @Test
//    fun `when deleteNews Should Not Exist in getBookmarkedNews`() =
//        mainCoroutineRule.runBlockingTest {
//            val sampleNews = DataDummy.generateDummyNewsEntity()[0]
//            storyDao.saveNews(sampleNews)
//            storyDao.deleteNews(sampleNews.title)
//            val actualNews = storyDao.getBookmarkedNews().getOrAwaitValue()
//            assertFalse(actualNews.contains(sampleNews))
//            assertFalse(storyDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
//        }
}