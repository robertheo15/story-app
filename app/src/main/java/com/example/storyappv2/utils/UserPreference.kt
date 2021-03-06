package com.example.storyappv2.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyappv2.network.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[userId] ?: "",
                preferences[name] ?: "",
                preferences[token] ?: "",
                preferences[isLogin] ?: false
            )
        }
    }

    suspend fun setUser(user: User) {
        dataStore.edit { preferences ->
            preferences[userId] = user.userId
            preferences[name] = user.name
            preferences[token] = user.token
            preferences[isLogin] = user.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[isLogin] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[userId] = ""
            preferences[name] = ""
            preferences[token] = ""
            preferences[isLogin] = false
        }
    }

    companion object {

        private val userId = stringPreferencesKey("userId")
        private val name = stringPreferencesKey("name")
        private val token = stringPreferencesKey("token")
        private val isLogin = booleanPreferencesKey("isLogin")
    }
}