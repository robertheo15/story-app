package com.example.storyappv2.view.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.example.storyappv2.network.UserResponse
import com.example.storyappv2.fake.DataDummy
import com.example.storyappv2.utils.UserPreference
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var userResponse: LiveData<UserResponse>
    private val dummyNews = DataDummy.generateLoginResponseSuccess()

    @Mock
    private lateinit var observer: Observer<in Boolean>

    @Mock
    private lateinit var isLoading: LiveData<Boolean>

    @Mock
    val pref =
        UserPreference.getInstance(requireContext(ApplicationProvider.getApplicationContext()).dataStore)

    @Before
    fun setUp() {


        loginViewModel = LoginViewModel(pref)
        isLoading = loginViewModel.isLoading
    }


    @Test
    fun `Verify livedata values changes on event`() {
        assertNotNull(loginViewModel.login("robertheo.rt@gmail.com", "123456"))
        loginViewModel.isLoading.observeForever(observer)
        verify(observer).onChanged(false)
        loginViewModel.login("robertheo.rt@gmail.com", "123456")
        verify(observer).onChanged(true)
    }

//    @Test
//    fun `when Login Should error Null and Return Success`() {
//        val observer = Observer<Result<UserResponse>>{}
//        try {
//            val expectedLogin = MutableLiveData<Result<UserResponse>>()
//            expectedLogin.value = Result.success(dummyNews)
////            `when`(loginViewModel.login("robertheo.rt@gmail.com", "123456"))
//
//            val actualNews = loginViewModel.login("robertheo.rt@gmail.com", "123456")
//            val actualResponse = loginViewModel.userResponse.observeForever(observer)
//
//            Mockito.verify(newsRepository)
//            Assert.assertNotNull(actualResponse).
//        } finally {
//            loginViewModel.login("robertheo.rt@gmail.com", "123456")
//            loginViewModel.userResponse.removeObserver(observer)
//        }
//    }
}