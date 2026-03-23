package com.example.lemon.security

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64

object PasswordHasher {

    private const val ITERATIONS = 120000
    private const val KEY_LENGTH = 256

    fun hash(password: String, salt: ByteArray = generateSalt()): String {
        val spec = PBEKeySpec(
            password.toCharArray(),
            salt,
            ITERATIONS,
            KEY_LENGTH
        )

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded

        return buildString {
            append(Base64.encodeToString(salt, Base64.NO_WRAP))
            append(":")
            append(Base64.encodeToString(hash, Base64.NO_WRAP))
        }
    }

    fun verify(password: String, stored: String): Boolean {
        val parts = stored.split(":")
        if (parts.size != 2) return false

        val salt = Base64.decode(parts[0], Base64.NO_WRAP)
        val expectedHash = hash(password, salt)

        return expectedHash == stored
    }

    private fun generateSalt(): ByteArray {
        return ByteArray(16).apply {
            SecureRandom().nextBytes(this)
        }
    }
}
