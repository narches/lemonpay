//package com.example.lemon.fund
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.lemon.network.AccountApi
//import com.example.lemon.network.ApiClient
//import com.example.lemon.network.models.FundRequest
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//
//class FundViewModel(
//    private val accountApi: AccountApi = ApiClient.ApiClientretrofit.create(AccountApi::class.java)
//) : ViewModel() {
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//
//    private val _message = MutableStateFlow<String?>(null)
//    val message: StateFlow<String?> = _message
//
//    /**
//     * Fund account
//     * phone = account number
//     * token = JWT (later moved to interceptor)
//     */
//    fun fundAccount(
//        phoneNumber: String,
//        amount: Double,
//        token: String
//    ) {
//        if (amount <= 0) {
//            _message.value = "Amount must be greater than zero"
//            return
//        }
//
//        viewModelScope.launch {
//            try {
//                _loading.value = true
//
//                accountApi.fund(
//                    FundRequest(
//                        toAccount = phoneNumber,
//                        amount = amount
//                    )
//                )
//
//                _message.value = "Account funded successfully"
//            } catch (e: Exception) {
//                _message.value = e.localizedMessage ?: "Funding failed"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//
//    fun clearMessage() {
//        _message.value = null
//    }
//}
