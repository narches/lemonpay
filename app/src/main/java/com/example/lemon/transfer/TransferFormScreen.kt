package com.example.lemon.transfer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
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
        modifier = Modifier.fillMaxSize().background(LemonBlack).padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Transfer", color = LemonYellow, style = MaterialTheme.typography.h5, modifier = Modifier
               .align(Alignment.CenterHorizontally))

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = state.phone,
            onValueChange = { onChange(it, state.amount, state.description) },
            label = { Text("Phone", color = LemonYellow) },


            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = LemonYellow,
                unfocusedBorderColor = LemonYellow,
                textColor = LemonYellow,
                cursorColor = LemonYellow,
                focusedLabelColor = LemonYellow,
                unfocusedLabelColor = LemonYellow
            )

        )

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = state.amount,
            onValueChange = { onChange(state.phone, it, state.description) },
            label = { Text("Amount", color = LemonYellow) },

            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = LemonYellow,
                unfocusedBorderColor = LemonYellow,
                textColor = LemonYellow,
                cursorColor = LemonYellow,
                focusedLabelColor = LemonYellow,
                unfocusedLabelColor = LemonYellow
            )
        )

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = state.description,
            onValueChange = { onChange(state.phone, state.amount, it) },
            label = { Text("Description", color = LemonYellow) },

            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = LemonYellow,
                unfocusedBorderColor = LemonYellow,
                textColor = LemonYellow,
                cursorColor = LemonYellow,
                focusedLabelColor = LemonYellow,
                unfocusedLabelColor = LemonYellow
            )
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onNext,
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = LemonYellow),
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(50.dp),
        ) {
            Text("Continue", color = LemonBlack)
        }
    }
}


