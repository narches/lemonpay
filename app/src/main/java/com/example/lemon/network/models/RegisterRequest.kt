package com.example.lemon.network

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.lemon.Home
import com.example.lemon.AuthRoutes
import com.example.lemon.datastore.DataStoreManager
import com.example.lemon.security.PasswordHasher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    dataStoreManager: DataStoreManager
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val scope = rememberCoroutineScope()

    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ---------- BIOMETRIC ----------
    val biometricManager = BiometricManager.from(context)
    val canUseBiometric =
        biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS

    val biometricPrompt = remember {
        BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    scope.launch {
                        dataStoreManager.setLoggedIn(true)
                        navController.navigate(Home.route) {
                            popUpTo(0)
                        }
                    }
                }
            }
        )
    }

    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login with Biometrics")
            .setSubtitle("Use fingerprint or face recognition")
            .setNegativeButtonText("Use password")
            .build()
    }

    // Auto-prompt biometric
    LaunchedEffect(Unit) {
        if (canUseBiometric) {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    // ---------- UI ----------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Login", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp)
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            onClick = {
                scope.launch {
                    val user = dataStoreManager.userFlow.first()

                    val valid =
                        user.phone == phone.trim() &&
                                PasswordHasher.verify(password, user.passwordHash)

                    if (valid) {
                        dataStoreManager.setLoggedIn(true)
                        navController.navigate(Home.route) {
                            popUpTo(0)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Invalid phone number or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        ) {
            Text("Login", fontSize = 18.sp)
        }

        if (canUseBiometric) {
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = {
                biometricPrompt.authenticate(promptInfo)
            }) {
                Text("Login with biometrics")
            }
        }

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = { navController.navigate(AuthRoutes.REGISTER) }
        ) {
            Text("Create an account")
        }
    }
}
