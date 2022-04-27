package com.example.storyappv2.view.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.storyappv2.data.UserRepository
import com.example.storyappv2.network.UserResponse
import com.example.storyappv2.fake.DataDummy
import com.example.storyappv2.network.User
import com.example.storyappv2.utils.MainCoroutineRule
import com.example.storyappv2.utils.UserPreference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateLoginResponseSuccess()

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(userRepository)
    }

    @Test
    fun `When User Login should Success and Return Success`() = runTest {
        val expectedResponse = flow {
            emit(Result.success(dummyLoginResponse))
        }

        `when`(loginViewModel.login("email@email.com", "password")).thenReturn(expectedResponse)

        loginViewModel.login("email@email.com", "password").collect { result ->

            assertTrue(result.isSuccess)
            assertFalse(result.isFailure)
            result.onSuccess { actualResponse ->
                assertNotNull(actualResponse)
                assertSame(dummyLoginResponse, actualResponse)
            }

            verify(userRepository).login("email@email.com", "password")
        }
    }

    @Test
    fun `When User Login Failed and Return Should Be Failed`() = runTest {
        val expectedResponse: Flow<Result<UserResponse>> =
            flowOf(Result.failure(Exception("Login Failed!")))

        `when`(loginViewModel.login("email@email.com", "password")).thenReturn(expectedResponse)

        loginViewModel.login("email@email.com", "password").collect { result ->

            assertFalse(result.isSuccess)
            assertTrue(result.isFailure)
            result.onFailure {
                assertNotNull(it)
            }

            verify(userRepository).login("email@email.com", "password")
        }
    }

    @Test
    fun `Set User session successfully`(): Unit = runTest {
        dummyLoginResponse.loginResult?.let { loginViewModel.setUser(it) }
        dummyLoginResponse.loginResult?.let { Mockito.verify(userRepository).setUser(it) }
    }
}