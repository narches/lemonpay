package com.example.lemon.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val imageUri: String = "",
    val isRegistered: Boolean = false
)

class DataStoreManager(private val context: Context) {
    companion object {
        private val KEY_NAME = stringPreferencesKey("name")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PASSWORD = stringPreferencesKey("password")
        private val KEY_IMAGE_URI = stringPreferencesKey("image_uri")
        private val KEY_REGISTERED = booleanPreferencesKey("is_registered")
    }

    val userFlow: Flow<UserProfile> = context.dataStore.data
        .map { prefs ->
            UserProfile(
                name = prefs[KEY_NAME] ?: "",
                email = prefs[KEY_EMAIL] ?: "",
                password = prefs[KEY_PASSWORD] ?: "",
                imageUri = prefs[KEY_IMAGE_URI] ?: "",
                isRegistered = prefs[KEY_REGISTERED] ?: false
            )
        }

    suspend fun saveUser(name: String, email: String, password: String, imageUri: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_NAME] = name
            prefs[KEY_EMAIL] = email
            prefs[KEY_PASSWORD] = password
            prefs[KEY_IMAGE_URI] = imageUri
            prefs[KEY_REGISTERED] = true
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
