package com.example.lemon.auth



import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.lemon.ui.theme.LemonBlack
import com.example.lemon.ui.theme.LemonYellow
import com.example.lemon.validation.AuthValidator


@Composable
fun RegistrationScreen(
    loading: Boolean,
    onRegister: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {

    val LemonYellow = Color(0xFFF4CE14)
    val LemonBlack = Color(0xFF000000)


    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val nameError =AuthValidator. validateName(name)
    val emailError = AuthValidator.validateEmail(email)
    val phoneError = AuthValidator.validatePhone(phoneNumber)
    val passwordError = AuthValidator.validatePassword(password)

    val isFormValid = nameError == null &&
            emailError == null &&
            phoneError == null &&
            passwordError == null

//                   VALIDATION
    val isNameValid = remember(name) {
        name.length >= 2 && name.matches(Regex("^[A-Za-z ]+$"))
    }

    val isEmailValid = remember(email) {
        email.matches(
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        )
    }

    val isPhoneValid = remember(phoneNumber) {
        phoneNumber.length == 11 &&
                phoneNumber.all { it.isDigit() } &&
                !phoneNumber.startsWith("x")
    }

    IconButton(onClick = { onBack() },
        modifier = Modifier.padding(top = 16.dp)) {
        Icon(Icons.Default.ArrowBack, "Back")
    }

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(11.dp))

        Text("Register", style = MaterialTheme.typography.h5, color = LemonBlack, modifier = Modifier.align(
            Alignment.CenterHorizontally))

        Spacer(Modifier.height(11.dp))
        OutlinedField(
            value = name,
            onValueChange = { name = it },
            label = "Name",
            isValid = isNameValid
        )
        ErrorText(nameError)
        Spacer(Modifier.height(8.dp))
        OutlinedField(
            value = phoneNumber,
            label = "Phone",
            isValid = isPhoneValid,
            onValueChange = {
                if (it.length <= 11 && it.all { c -> c.isDigit() }) {
                    phoneNumber = it
                }
            }
        )
        ErrorText(phoneError)
        Spacer(Modifier.height(8.dp))
        OutlinedField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            isValid = isEmailValid,

        )
        ErrorText(emailError)

        Spacer(Modifier.height(8.dp))

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
        ErrorText(passwordError)

        Spacer(Modifier.height(20.dp))

        Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =
                        if (isFormValid && !loading) LemonBlack
                        else LemonBlack.copy(alpha = 0.4f),
                    contentColor = LemonYellow
                ),
                shape = RoundedCornerShape(25.dp),
            onClick = {
                onRegister(name, email, phoneNumber, password)
            },
        ) {
            Text(if (loading) "Creating..." else "Register")
        }
    }
}


@Composable
private fun OutlinedField(
    value: String,
    label: String,
    isValid: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = LemonBlack) },
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
}

@Composable
private fun ErrorText(error: String?) {
    if (error != null) {
        Text(
            text = error,
            color = MaterialTheme.colors.error,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp, top = 2.dp)
        )
    }
}