package com.example.lemon.withdraw


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WithdrawScreen(
    phoneNumber: String,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: WithdrawViewModel = viewModel()

    val loading by viewModel.loading.collectAsState()
    val message by viewModel.message.collectAsState()

    var amount by remember { mutableStateOf("") }

    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            if (it.contains("success", ignoreCase = true)) {
                onSuccess()
            }
            viewModel.clearMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Withdraw Funds", style = MaterialTheme.typography.h5)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.withdraw(
                    phoneNumber = phoneNumber,
                    amount = amount.toDoubleOrNull() ?: 0.0
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !loading
        ) {
            Text(if (loading) "Processing..." else "Withdraw")
        }
    }
}
