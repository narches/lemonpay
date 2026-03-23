package com.example.lemon.transfer




import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TransferLoadingScreen() {
    val LemonBlack = Color(0xFF000000)
    val LemonYellow = Color(0xFFF4CE14)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonBlack),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = LemonYellow
        )
    }
}