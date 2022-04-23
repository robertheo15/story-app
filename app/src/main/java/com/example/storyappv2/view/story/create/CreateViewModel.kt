package com.example.storyappv2.view.story.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyappv2.network.api.ApiConfig
import com.example.storyappv2.network.FileUploadResponse
import com.example.storyappv2.network.User
import com.example.storyappv2.utils.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateViewModel(private val pref: UserPreference) : ViewModel() {

    private val _fileUploadResponse = MutableLiveData<FileUploadResponse>()
    val fileUploadResponse: LiveData<FileUploadResponse> = _fileUploadResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun uploadImage(token: String, description: RequestBody, imageMultipart: MultipartBody.Part) {

        _isLoading.value = true
        val service =
            ApiConfig.getApiService().uploadImage("Bearer $token", description, imageMultipart)
        service.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                if (response.isSuccessful) {
                    _fileUploadResponse.value = response.body()

                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _fileUploadResponse.value =
                        FileUploadResponse(jsonObj.getBoolean("error"), jsonObj.getString("message"))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "CreateViewModel"
    }
}