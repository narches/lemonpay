package com.example.lemon.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lemon.R
import com.example.lemon.datastore.DataStoreManager
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.lemon.Home
import com.example.lemon.datastore.UserProfile
import kotlinx.coroutines.launch




@Composable
fun ProfileScreen(navController: NavController, dataStore: DataStoreManager) {
    val scope = rememberCoroutineScope()
    val user by dataStore.userFlow.collectAsState(initial = UserProfile())

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(8.dp))
        if (user.imageUri.isNotEmpty()) {
            AsyncImage(model = user.imageUri, contentDescription = "profile", modifier = Modifier.size(120.dp).clip(CircleShape))
        } else {
            Image(painter = painterResource(id = R.drawable.lemon), contentDescription = "default", modifier = Modifier.size(120.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(user.name.ifBlank { "No name" }, style = MaterialTheme.typography.h6)
        Text(user.email.ifBlank { "No email" }, style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            scope.launch {
                dataStore.clearUser()
                navController.navigate("login") { popUpTo(Home.route) { inclusive = true } }
            }
        }, modifier = Modifier.fillMaxWidth()
            .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF495E57))

        ) {
            Text("Log out")
        }
    }
}