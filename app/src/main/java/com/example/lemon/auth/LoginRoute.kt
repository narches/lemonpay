package com.example.lemon.auth


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lemon.navigation.Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.VisualTransformation
import com.example.lemon.ui.theme.LemonBlack
import com.example.lemon.ui.theme.LemonYellow
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lemon.validation.AuthValidator


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.loginSuccess.collectAsState()
    val LemonYellow = Color(0xFFF4CE14)
    val LemonBlack = Color(0xFF111111)

    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }


    val isPhoneValid = isValidPhone(phoneNumber)
    val isPasswordValid = isValidPassword(password)
    val phoneError = AuthValidator.validatePhone(phoneNumber)
    val passwordError = AuthValidator.validatePassword(password)

    val canLogin = isPhoneValid && isPasswordValid && !loading
    val isFormValid = phoneError == null && passwordError == null



    LaunchedEffect(success) {
        if (success) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
            viewModel.consumeLoginSuccess()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.consumeError()
        }
    }


    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.height(10.dp))

        Text("Login", style = MaterialTheme.typography.h5, color = LemonBlack,
            modifier = Modifier
                .align(
            Alignment.CenterHorizontally))

        Spacer(Modifier.height(16.dp))

        LemonOutlinedField(
            value = phoneNumber,
            label = "Phone",
            onValueChange = {
                if (it.length <= 11 && it.all { c -> c.isDigit() }) phoneNumber = it
            },
            modifier = Modifier.fillMaxWidth(),
//                .height(50.dp),
            shape = RoundedCornerShape(25.dp)

//            isError = phone.isNotBlank() && !isPhoneValid
        )

        if (phoneNumber.isNotBlank() && !isPhoneValid) {
            Text(
                text = "Enter a valid phone number",
                color = MaterialTheme.colors.error,
                fontSize = 12.sp
            )

        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = LemonBlack) },
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector =
                            if (passwordVisible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                        contentDescription = "Toggle password",
                        tint = LemonBlack
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = LemonYellow,
                    unfocusedBorderColor = LemonBlack,
                    textColor = LemonBlack,
                    cursorColor = LemonBlack,
                    focusedLabelColor = LemonBlack,
                    unfocusedLabelColor = LemonBlack
                )
        )

        Spacer(Modifier.height(20.dp))
        Button(
                shape = RoundedCornerShape(25.dp),
            enabled = !loading && isFormValid,
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                    if (isFormValid && !loading) LemonBlack
                    else LemonBlack.copy(alpha = 0.4f),
                contentColor = LemonYellow
            ),
            onClick = {
                viewModel.login(phoneNumber.trim(), password)
            },
            modifier = Modifier.fillMaxWidth()
                .height(50.dp),
        ) {
            Text(if (loading) "Logging in..." else "Login")
        }

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = { navController.navigate(Routes.REGISTER) }
        ) {
            Text("Create an account", color = LemonYellow)
        }
    }
}

@Composable
private fun LemonOutlinedField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    shape: RoundedCornerShape
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = LemonBlack) },
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = LemonYellow,
            unfocusedBorderColor = LemonBlack,
            textColor = LemonBlack,
            cursorColor = LemonBlack,
            focusedLabelColor = LemonBlack,
            unfocusedLabelColor = LemonBlack
        )
    )
}

fun isValidPhone(phone: String): Boolean {
    return phone.length == 11 && phone.all { it.isDigit() } && !phone.startsWith("+")
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}