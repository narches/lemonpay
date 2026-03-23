package com.example.lemon.transfer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color





@Composable
fun TransferFormScreen(
    state: TransferUiState.Form,
    onChange: (String, String, String) -> Unit,
    onNext: () -> Unit
) {


    val LemonYellow = Color(0xFFF4CE14)
    val LemonBlack = Color(0xFF000000)

    Column(
        Modifier.fillMaxSize().background(LemonBlack).padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Transfer", color = LemonYellow)

        OutlinedTextField(
            value = state.phone,
            shape = RoundedCornerShape(25.dp),
            onValueChange = { onChange(it, state.amount, state.description) },
            label = { Text("Phone", color = LemonYellow) }
        )

        OutlinedTextField(
            value = state.amount,
            shape = RoundedCornerShape(25.dp),
            onValueChange = { onChange(state.phone, it, state.description) },
            label = { Text("Amount", color = LemonYellow) }
        )

        OutlinedTextField(
            value = state.description,
            shape = RoundedCornerShape(25.dp),
            onValueChange = { onChange(state.phone, state.amount, it) },
            label = { Text("Description", color = LemonYellow) }
        )

        Button(
            onClick = onNext,
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = LemonYellow)
        ) {
            Text("Continue", color = LemonBlack)
        }
    }
}