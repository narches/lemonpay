
//
//package com.example.lemon.fund
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//
//@Composable
//fun FundScreen(
//    navController: NavController,
//    phoneNumber: String,
//    viewModel: FundViewModel = viewModel()
//) {
//    val context = LocalContext.current
//
//    val loading by viewModel.loading.collectAsState()
//    val success by viewModel.success.collectAsState()
//    val error by viewModel.error.collectAsState()
//
//    var amount by remember { mutableStateOf("") }
//
//    LaunchedEffect(success) {
//        if (success) {
//            Toast.makeText(
//                context,
//                "Account funded successfully",
//                Toast.LENGTH_SHORT
//            ).show()
//            navController.popBackStack()
//        }
//    }
//
//    LaunchedEffect(error) {
//        error?.let {
//            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//            viewModel.clearError()
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        Text(
//            text = "Fund Account",
//            style = MaterialTheme.typography.h5
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = amount,
//            onValueChange = { amount = it },
//            label = { Text("Amount") },
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            enabled = !loading,
//            onClick = {
//                viewModel.fundAccount(
//                    phone = myAccountNumber,
//                    amount = amount.toDoubleOrNull() ?: 0.0
//                )
//            }
//        ) {
//            Text(if (loading) "Processing..." else "Fund Account")
//        }
//    }
//}
