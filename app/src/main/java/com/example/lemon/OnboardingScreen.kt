package com.example.lemon.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lemon.R
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.height
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController





@Composable
fun OnboardingScreen(
    navController: NavController,
    prefs: SharedPreferences,
    onRegistered: () -> Unit
) {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        // Logo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logox),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.height(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Green banner
        Box(
            modifier = Modifier
//                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF495E57))
        ) {
            Text(
                "Let’s get to know you",
                modifier = Modifier
                    .align(Alignment.Center),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(150.dp))

        Text(
            "Personal Information",
            modifier = Modifier
                .padding(horizontal = 24.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Text Fields
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name") },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)

        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(250.dp))

        // Register Button
        Button(
            onClick = {
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(
                        context,
                        " Please enter all data.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }

                prefs.edit().apply {
                    putString("firstname", firstName)
                    putString("lastname", lastName)
                    putString("email", email)
                    putBoolean("isRegistered", true)
                    apply()
                }

                Toast.makeText(
                    context,
                    "Registration successful.",
                    Toast.LENGTH_LONG
                ).show()

                onRegistered()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFF4CE14)
            )
        ) {
            Text(
                "Register",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
