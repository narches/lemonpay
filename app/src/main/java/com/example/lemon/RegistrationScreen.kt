package com.example.lemon

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import com.example.lemon.datastore.DataStoreManager
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun RegistrationScreen(navController: NavController, dataStoreManager: DataStoreManager) {

    val scope = rememberCoroutineScope()

    // User input values
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Picked profile image
    var pickedUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pickedUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Show picked image or placeholder
        if (pickedUri != null) {
            AsyncImage(
                model = pickedUri,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.lemon),
                contentDescription = "avatar placeholder",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF495E57)
            ), onClick = { launcher.launch("image/*") }
        ) {
            Text("Pick photo", color = Color(0xFFF4CE14))
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button( modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF495E57)),
            onClick = {
                if (fullName.isBlank() || email.isBlank()) return@Button

                scope.launch {
                    val imageStr = pickedUri?.toString() ?: ""

                    dataStoreManager.saveUser(
                        name = fullName.trim(),
                        email = email.trim(),
                        password = password.trim(),
                        imageUri = imageStr)

                    // After successful registration → move to Home
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }
            },
        ) {
            Text("Register", color = Color(0xFFF4CE14), fontSize = 20.sp)
        }
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Login", color = Color(0xFF495E57), fontSize = 20.sp)
        }
    }
}