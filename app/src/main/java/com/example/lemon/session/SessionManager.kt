package com.example.lemon.session

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val KEY_PHONE = "PHONE_NUMBER"
        private const val KEY_NAME = "USER_NAME"

        private const val KEY_EMAIL = "EMAIL"
    }

    /* -------------------- SAVE -------------------- */

    fun saveSession(
        accessToken: String,
        phoneNumber: String,
        email: String,
        name: String
    ) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_PHONE, phoneNumber)
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    /* -------------------- READ -------------------- */

    fun getAccessToken(): String? =
        prefs.getString(KEY_ACCESS_TOKEN, null)

    fun getPhoneNumber(): String? =
        prefs.getString(KEY_PHONE, null)

    fun getUserName(): String? =
        prefs.getString(KEY_NAME, null)

    fun getEmail(): String? =
        prefs.getString(KEY_EMAIL, null)

    /**
     * User is logged in if a JWT exists.
     */
    fun isLoggedIn(): Boolean =
        !getAccessToken().isNullOrBlank()

    /* -------------------- REQUIRE -------------------- */

    fun requirePhoneNumber(): String =
        getPhoneNumber()
            ?: throw IllegalStateException("Phone number not found in session")

    fun requireUserName(): String =
        getUserName()
            ?: throw IllegalStateException("Username not found in session")

    fun requireEmail(): String =
        getEmail()
            ?: throw IllegalStateException("Email not found in session")

    fun requireAccessToken(): String =
        getAccessToken()
            ?: throw IllegalStateException("Access token not found in session")

    /* -------------------- CLEAR -------------------- */

    fun clear() {
        prefs.edit().clear().apply()
    }
}
