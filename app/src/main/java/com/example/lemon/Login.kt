package com.example.lemon

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import androidx.navigation.NavController
import com.example.lemon.datastore.DataStoreManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch






@Composable
fun LoginScreen(navController: NavController, dataStoreManager: DataStoreManager) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize() .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Login", style = MaterialTheme.typography.h5,  textAlign = TextAlign.Center,)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(25.dp),)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(25.dp),)
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            scope.launch {
                val user = dataStoreManager.userFlow.first()
                if (user.email.isNotBlank() && user.email == email.trim() /* && (optional) check password */) {
                    // success -> go to Home (old app)
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
            }, modifier = Modifier.fillMaxWidth()
                            .height(50.dp),
               shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                      backgroundColor = Color(0xFF495E57)
            )
        ) {
                Text("Login", color = Color(0xFFF4CE14), fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column {
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Sign up",   textAlign = TextAlign.Center, color = Color(0xFF495E57), fontSize = 20.sp)
            }
        }
    }
}

