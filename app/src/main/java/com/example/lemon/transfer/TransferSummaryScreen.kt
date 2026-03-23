package com.example.lemon.transfer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color




@Composable
fun TransferSummaryScreen(
    state: TransferUiState.Summary,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    val LemonYellow = Color(0xFFF4CE14)
    val LemonBlack = Color(0xFF000000)

    Column(
        Modifier.fillMaxSize().background(LemonBlack).padding(25.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Confirm Transfer", style = MaterialTheme.typography.h5, color = LemonYellow,
            modifier = Modifier.align(
                Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        Text("To: ${state.phone}", color = LemonYellow, modifier = Modifier.align(
            Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        Text("Amount: ${state.amount}", color = LemonYellow, modifier = Modifier.align(
            Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        Text("Description: ${state.description}", color = LemonYellow, modifier = Modifier.align(
            Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        Row ( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onBack, shape = RoundedCornerShape(25.dp),  modifier = Modifier.weight(1f)
                .height(50.dp), colors = ButtonDefaults.buttonColors(backgroundColor = LemonYellow)) { Text("Edit",  color = LemonBlack) }
            Spacer(Modifier.width(20.dp))
            Button(onClick = onConfirm, shape = RoundedCornerShape(25.dp),  modifier = Modifier.weight(1f)
                .height(50.dp),   colors = ButtonDefaults.buttonColors(backgroundColor = LemonYellow)) { Text("Confirm",  color = LemonBlack) }
        }
    }
}

