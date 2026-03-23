package com.example.lemon.transfer

sealed interface TransferEvent {
    data class Toast(val message: String) : TransferEvent
}