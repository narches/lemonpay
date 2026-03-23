package com.example.lemon.transfer

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.lemon.transfer.receipt.generateBrandedTransactionPdf

@Composable
fun TransferRoute(
    navController: NavController,
    viewModel: TransferViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            if (it is TransferEvent.Toast) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    when (uiState) {
        is TransferUiState.Form ->
            TransferFormScreen(
                state = uiState as TransferUiState.Form,
                onChange = viewModel::updateForm,
                onNext = viewModel::proceedToSummary
            )

        is TransferUiState.Summary -> {
            val summary = uiState as TransferUiState.Summary


            TransferSummaryScreen(
                state = uiState as TransferUiState.Summary,
                onBack = {
                    viewModel.updateForm(
                        summary.phone,
                        summary.amount,
                        summary.description
                    )
                },
                onConfirm = viewModel::submitTransfer
            )

        }

        is TransferUiState.Loading ->
            TransferLoadingScreen()

        is TransferUiState.Success -> {
            val success = uiState as TransferUiState.Success

            TransferSuccessScreen(
                state = success,
                onViewReceipt = {
                    val pdfFile = generateBrandedTransactionPdf(context, success)

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("receipt_pdf", pdfFile)

                    navController.navigate("receipt-preview")
                },
                onDone = {
                    navController.navigate("HOME") {
                        popUpTo("transfer") { inclusive = true }
                    }
                }
            )
        }
    }
}
