package com.example.lemon.transfer




import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TransferSuccessScreen(
    state: TransferUiState.Success,
    onViewReceipt: () -> Unit,
    onDone: () -> Unit
) {
    val LemonBlack = Color(0xFF000000)
    val LemonYellow = Color(0xFFF4CE14)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonBlack)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Transfer Successful",
            style = MaterialTheme.typography.h5,
            color = LemonYellow
        )

        Spacer(modifier = Modifier.height(16.dp))

        SuccessRow("Reference", state.reference)
        Spacer(modifier = Modifier.height(5.dp))
        SuccessRow("To", state.phone)
        Spacer(modifier = Modifier.height(5.dp))
        SuccessRow("Amount", state.amount)
        Spacer(modifier = Modifier.height(5.dp))
        SuccessRow("Date", state.timestamp)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onViewReceipt,
            modifier = Modifier.fillMaxWidth()
            .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LemonYellow,
                contentColor = LemonBlack
            )
        ) {
            Text("View Receipt")
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth()
            .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = LemonYellow
            )
        ) {
            Text("Done")
        }
    }
}


@Composable
private fun SuccessRow(
    label: String,
    value: String
) {
    val LemonYellow = Color(0xFFF4CE14)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = LemonYellow)
        Text(text = value, color = LemonYellow)
    }
}