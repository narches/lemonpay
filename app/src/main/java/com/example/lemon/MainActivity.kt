

package com.example.lemon

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import com.example.lemon.navigation.Routes
import com.example.lemon.auth.LoginRoute
import com.example.lemon.auth.LogoutRoute
import com.example.lemon.auth.RegistrationRoute
import com.example.lemon.overview.OverviewRoute
import com.example.lemon.profile.ProfileRoute
import com.example.lemon.transactions.TransactionDetailRoute
import com.example.lemon.transactions.TransactionRoute
import com.example.lemon.transfer.TransferRoute
import com.example.lemon.transfer.receipt.ReceiptPreviewScreen
import com.example.lemon.transfer.receipt.downloadPdf
import com.example.lemon.ui.toast.CenteredAnimatedToast
import com.example.lemon.ui.toast.ToastManager
import com.example.lemon.ui.toast.UiToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        }
        setContent {
            AppEntry()

            AppToastHost()
        }
    }
}


@Composable
private fun AppEntry() {
    val navController = rememberNavController()



    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.INDEX) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.INDEX) {
            IndexScreen(navController = navController)
        }

        composable(Routes.LOGIN) {
            LoginRoute(navController)
        }

        composable(Routes.USER) {
            ProfileRoute(navController)
        }

        composable(Routes.REGISTER) {
            RegistrationRoute(navController)
        }

        composable(Routes.TRANSFER) {
            TransferRoute(navController)
        }


        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }


        composable(Routes.OVERVIEW) {
            OverviewRoute()
        }


        composable("receipt-preview") {

            val navBackStackEntry = navController.previousBackStackEntry
                ?: return@composable
            val context = LocalContext.current
            val pdfFile =
                navBackStackEntry.savedStateHandle
                    .get<File>("receipt_pdf")
                    ?: return@composable

            ReceiptPreviewScreen(
                pdfFile = pdfFile,
                onDownload = {
                    downloadPdf(
                        context,
                        sourceFile = pdfFile,
                        fileName = "lemon_receipt_${System.currentTimeMillis()}.pdf"
                    )
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }


        composable(Routes.TRANSACTIONS){
            TransactionRoute(
                navController = navController,
                onTransactionClick = { tx ->
                    navController.navigate("${Routes.TRANSACTION_DETAIL}/${tx.reference}")
                }
            )
        }

        composable("${Routes.TRANSACTION_DETAIL}/{reference}") { backStackEntry ->
            val reference =
                backStackEntry.arguments!!.getString("reference")!!

            TransactionDetailRoute(
                reference = reference,
                onBack = { navController.popBackStack() }
            )
        }

    }
}


@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4CE14)), // bright yellow
        contentAlignment = Alignment.Center
    ) {
        // Image on top
        Image(
            painter = painterResource(id = R.drawable.llogo), // replace with your image
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp) // adjust size as needed
        )
    }
}






@Composable
fun AppToastHost() {

    var toast by remember { mutableStateOf<UiToast?>(null) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        ToastManager.events.collect { event ->
            toast = event
            visible = true

            delay(1800)

            visible = false
            delay(300)

            toast = null
        }
    }

    toast?.let {
        CenteredAnimatedToast(
            toast = it,
            visible = visible
        )
    }
}