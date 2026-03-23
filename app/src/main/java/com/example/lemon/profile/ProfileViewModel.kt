package com.example.lemon.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemon.repository.TransferRepository
import com.example.lemon.transfer.TransferEvent
import com.example.lemon.transfer.TransferUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val repository: TransferRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<TransferUiState>(TransferUiState.Form())
    val uiState: StateFlow<TransferUiState> = _uiState

    private val _events = MutableSharedFlow<TransferEvent>()
    val events = _events.asSharedFlow()

    fun updateForm(phone: String, amount: String, description: String) {
        _uiState.value = TransferUiState.Form(phone, amount, description)
    }

    fun proceedToSummary() {
        val state = _uiState.value as? TransferUiState.Form ?: return
        val amount = state.amount.toBigDecimalOrNull()

        if (amount == null || amount <= BigDecimal.ZERO) {
            toast("Enter a valid amount")
            return
        }

        if (state.phone.length != 11) {
            toast("Invalid phone number")
            return
        }

        _uiState.value =
            TransferUiState.Summary(state.phone, amount.toString(), state.description)
    }

    fun submitTransfer() {
        val state = _uiState.value as? TransferUiState.Summary ?: return

        viewModelScope.launch {
            _uiState.value = TransferUiState.Loading

            runCatching {
                repository.makeTransfer(
                    state.phone,
                    state.amount,
                    state.description
                )
            }.onSuccess {
                toast("Transfer successful")

                _uiState.value = TransferUiState.Success(
                    reference = it.reference,
                    phone = it.toPhoneNumber,
                    amount = it.amount,
                    description = state.description,
                    timestamp = it.createdAt
                )
            }.onFailure {
                toast(it.message ?: "Transfer failed")
                _uiState.value = TransferUiState.Form()
            }
        }
    }

    private fun toast(msg: String) {
        viewModelScope.launch {
            _events.emit(TransferEvent.Toast(msg))
        }
    }
}