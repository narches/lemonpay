package com.example.lemon.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemon.network.AuthService
import com.example.lemon.network.models.LoginRequest
import com.example.lemon.network.models.RegisterRequest
import com.example.lemon.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.lemon.session.SessionManager
import com.example.lemon.ui.toast.UiToast
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    /* ---------------- UI STATE ---------------- */

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    private val _registerSuccess = MutableSharedFlow<Unit>()
    val registerSuccess = _registerSuccess.asSharedFlow()

    private val _logoutSuccess = MutableSharedFlow<Unit>()
    val logoutSuccess = _logoutSuccess.asSharedFlow()

    private val _toastEvent = MutableSharedFlow<UiToast>()
    val toastEvent = _toastEvent.asSharedFlow()

    /* ---------------- LOGIN ---------------- */

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            executeAuthAction(
                action = {
                    authRepository.login(phoneNumber, password)
                },
                successToast = "Login successful",
                successEvent = { _loginSuccess.emit(Unit) }
            )
        }
    }

    /* ---------------- REGISTER ---------------- */

    fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String
    ) {
        viewModelScope.launch {
            executeAuthAction(
                action = {
                    authRepository.register(
                        name = name,
                        email = email,
                        phone = phoneNumber,
                        password = password
                    )
                },
                successToast = "Registration successful",
                successEvent = { _registerSuccess.emit(Unit) }
            )
        }
    }

    /* ---------------- LOGOUT ---------------- */

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()

            _toastEvent.emit(
                UiToast.Success("Logged out successfully")
            )

            _logoutSuccess.emit(Unit)
        }
    }

    /* ---------------- SHARED EXECUTION ---------------- */

    private suspend fun executeAuthAction(
        action: suspend () -> Unit,
        successToast: String,
        successEvent: suspend () -> Unit
    ) {
        _loading.value = true
        _error.value = null

        try {
            action()

            _toastEvent.emit(UiToast.Success(successToast))
            successEvent()

        } catch (e: ClientRequestException) {
            val message = when (e.response.status.value) {
                401 -> "Invalid credentials"
                409 -> "Account already exists"
                else -> "Request failed (${e.response.status.value})"
            }
            handleError(message)

        } catch (e: ServerResponseException) {
            handleError("Server error. Try again later.")

        } catch (e: IOException) {
            handleError("Network error. Check your connection.")

        } catch (e: Exception) {
            handleError(e.localizedMessage ?: "Unexpected error")

        } finally {
            _loading.value = false
        }
    }

    private suspend fun handleError(message: String) {
        _error.value = message
        _toastEvent.emit(UiToast.Error(message))
    }
}

























































//
//@HiltViewModel
//class AuthViewModel @Inject constructor(
//    private val sessionManager: SessionManager,
//    private val authService: AuthService
//) : ViewModel() {
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//    private val _registerSuccess = MutableSharedFlow<Unit>()
//    val registerSuccess = _registerSuccess.asSharedFlow()
//
//    private val _loginSuccess = MutableSharedFlow<Unit>()
//    val loginSuccess = _loginSuccess.asSharedFlow()
//
//    private val _toastEvent = MutableSharedFlow<UiToast>()
//    val toastEvent = _toastEvent.asSharedFlow()
//
//    /* ---------------- LOGIN ---------------- */
//
//    fun login(
//        phoneNumber: String,
//        password: String
//    ) {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//
//            try {
//                val response = authService.login(
//                    request = LoginRequest(
//                        phoneNumber = phoneNumber,
//                        password = password
//                    )
//                )
//
//                sessionManager.saveSession(
//                    accessToken = response.token,
//                    phoneNumber = response.phoneNumber,
//                    name = response.name,
//                    email = response.email
//                )
//
//                // ✅ Toast feedback
//                _toastEvent.emit(
//                    UiToast.Success("Login successful")
//                )
//successful
//                // ✅ Navigation event
//                _loginSuccess.emit(Unit)
//
//            } catch (e: ClientRequestException) {
//                val message = when (e.response.status.value) {
//                    401 -> "Invalid credentials"
//                    else -> "Login failed (${e.response.status.value})"
//                }
//
//                _error.value = message
//
//                _toastEvent.emit(
//                    UiToast.Error(message)
//                )
//
//            } catch (e: ServerResponseException) {
//                _error.value = "Server error. Try again later."
//
//                _toastEvent.emit(
//                    UiToast.Error("Server error. Try again later.")
//                )
//
//            } catch (e: IOException) {
//                _error.value = "Network error. Check connection."
//
//                _toastEvent.emit(
//                    UiToast.Error("Network error. Check connection.")
//                )
//
//            } catch (e: Exception) {
//                val message = e.localizedMessage ?: "Unexpected error"
//                _error.value = message
//
//                _toastEvent.emit(
//                    UiToast.Error(message)
//                )
//
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//
//
//    /* ---------------- REGISTER ---------------- */
//    fun register(
//        name: String,
//        email: String,
//        phoneNumber: String,
//        password: String
//    ) {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//
//            try {
//                authService.register(
//                    RegisterRequest(
//                        name = name,
//                        email = email,
//                        phoneNumber = phoneNumber,
//                        password = password
//                    )
//                )
//
//                _toastEvent.emit(
//                    UiToast.Success("Registration successful")
//                )
//
//                _registerSuccess.emit(Unit)
//
//            } catch (e: ClientRequestException) {
//                val message =
//                    if (e.response.status.value == 409)
//                        "Account already exists"
//                    else
//                        "Registration failed"
//
//                _error.value = message
//
//                _toastEvent.emit(
//                    UiToast.Error(message)
//                )
//
//            } catch (e: ServerResponseException) {
//                _error.value = "Server error. Try again later."
//
//                _toastEvent.emit(
//                    UiToast.Error("Server error. Try again later.")
//                )
//
//            } catch (e: IOException) {
//                _error.value = "Network error. Check connection."
//
//                _toastEvent.emit(
//                    UiToast.Error("Network error. Check connection.")
//                )
//
//            } catch (e: Exception) {
//                _error.value = "Unexpected error"
//
//                _toastEvent.emit(
//                    UiToast.Error("Unexpected error")
//                )
//
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//}
//
