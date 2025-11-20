package com.example.lemon.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lemon.R
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp


@Composable
fun OnboardingScreen(
//    navController: NavController,
//    dataStoreManager: DataStoreManager,
    onSkip: () -> Unit,
    onRegister: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.salad),
            contentDescription = "Onboarding image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier.fillMaxWidth()
                    .height(30.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF495E57))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        "Welcome to Lemon!",
                        fontWeight = Bold,
                        style = MaterialTheme.typography.h4, color = Color(0xFFF4CE14)
                    )
                    Text(
                        "Discover food, order quickly and enjoy.",
                        style = MaterialTheme.typography.h6, color = Color(0xFFF4CE14)
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onRegister,
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF495E57)

                    )
                ) {
                    Text("Continue", color = Color(0xFFF4CE14),  fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(10.dp))

                TextButton(
                    onClick =
                        onSkip,
                ) {
                    Text("Skip", color = Color(0xFFFFFFFF),  fontSize = 20.sp)
                }

            }
        }
    }
}
