
package com.example.lemon.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    /* ---------------- DATA MODEL ---------------- */

    data class UserSession(
        val name: String,
        val email: String,
        val phoneNumber: String,
        val token: String,
        val isLoggedIn: Boolean
    )

    /* ---------------- KEYS ---------------- */

    companion object {
        private val KEY_NAME = stringPreferencesKey("name")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PHONE = stringPreferencesKey("phone")
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    /* ---------------- OBSERVE SESSION ---------------- */

    val sessionFlow: Flow<UserSession> = context.dataStore.data.map { prefs ->
        UserSession(
            name = prefs[KEY_NAME] ?: "",
            email = prefs[KEY_EMAIL] ?: "",
            phoneNumber = prefs[KEY_PHONE] ?: "",
            token = prefs[KEY_TOKEN] ?: "",
            isLoggedIn = prefs[KEY_LOGGED_IN] ?: false
        )
    }

    /* ---------------- SAVE SESSION (LOGIN SUCCESS) ---------------- */

    suspend fun saveSession(
        name: String,
        email: String,
        phoneNumber: String,
        token: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[KEY_NAME] = name
            prefs[KEY_EMAIL] = email
            prefs[KEY_PHONE] = phoneNumber
            prefs[KEY_TOKEN] = token
            prefs[KEY_LOGGED_IN] = true
        }
    }

    /* ---------------- LOGOUT ---------------- */

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = ""
            prefs[KEY_LOGGED_IN] = false
        }
    }

    /* ---------------- CLEAR ALL (DEBUG / RESET) ---------------- */

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
