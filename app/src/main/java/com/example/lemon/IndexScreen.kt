//package com.example.lemon.onboarding
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Button
//import androidx.compose.material.Text
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.lemon.R
//import androidx.compose.ui.graphics.Color
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.*
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.foundation.layout.height
//import android.widget.Toast
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import android.content.SharedPreferences
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.platform.LocalContext
//import androidx.navigation.NavController
//
//
//
//
//
//@Composable
//fun OnboardingScreen(
//    navController: NavController,
//    prefs: SharedPreferences,
//    onRegistered: () -> Unit
//) {
//    val context = LocalContext.current
//
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//
//        Spacer(modifier = Modifier.height(25.dp))
//
//        // Logo
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.logox),
//                contentDescription = "Little Lemon Logo",
//                modifier = Modifier.height(40.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Green banner
//        Box(
//            modifier = Modifier
////                .padding(top = 24.dp)
//                .fillMaxWidth()
//                .height(60.dp)
//                .background(Color(0xFF495E57))
//        ) {
//            Text(
//                "Let’s get to know you",
//                modifier = Modifier
//                    .align(Alignment.Center),
//                color = Color.White,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        Spacer(modifier = Modifier.height(150.dp))
//
//        Text(
//            "Personal Information",
//            modifier = Modifier
//                .padding(horizontal = 24.dp),
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Text Fields
//        OutlinedTextField(
//            value = firstName,
//            onValueChange = { firstName = it },
//            label = { Text("First name") },
//            shape = RoundedCornerShape(25.dp),
//            modifier = Modifier.fillMaxWidth()
//                .padding(horizontal = 24.dp)
//
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = lastName,
//            onValueChange = { lastName = it },
//            label = { Text("Last name") },
//            shape = RoundedCornerShape(25.dp),
//            modifier = Modifier.fillMaxWidth()
//                .padding(horizontal = 24.dp)
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            shape = RoundedCornerShape(25.dp),
//            modifier = Modifier.fillMaxWidth()
//                .padding(horizontal = 24.dp)
//        )
//
//        Spacer(modifier = Modifier.height(250.dp))
//
//        // Register Button
//        Button(
//            onClick = {
//                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
//                    Toast.makeText(
//                        context,
//                        " Please enter all data.",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    return@Button
//                }
//
//                prefs.edit().apply {
//                    putString("firstname", firstName)
//                    putString("lastname", lastName)
//                    putString("email", email)
//                    putBoolean("isRegistered", true)
//                    apply()
//                }
//
//                Toast.makeText(
//                    context,
//                    "Registration successful.",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                onRegistered()
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 24.dp)
//                .height(56.dp),
//            shape = RoundedCornerShape(25.dp),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color(0xFFF4CE14)
//            )
//        ) {
//            Text(
//                "Register",
//                color = Color.Black,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}
//
//


package com.example.lemon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lemon.AuthRoutes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndexScreen(
    navController: NavController
) {
    val images = listOf(
        R.drawable.greek,
        R.drawable.gllemon,
        R.drawable.saus
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { images.size }
    )

    // Optional auto-slide (bank-style)
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            pagerState.animateScrollToPage(
                (pagerState.currentPage + 1) % images.size
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ---------- SLIDABLE BACKGROUND ----------
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // ---------- DARK OVERLAY ----------
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.55f)
                        )
                    )
                )
        )

        // ---------- TOP RIGHT LOGO ----------
//        Image(
//            painter = painterResource(id = R.drawable.llogo),
//            contentDescription = "Bank Logo",
//            modifier = Modifier
//                .size(64.dp)
//                .align(Alignment.TopEnd)
//                .padding(16.dp)
//        )

        // ---------- CENTER CONTENT ----------
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "eazyLinks",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Personalize your banking experience by choosing your preferred banking services.",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(AuthRoutes.LOGIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Log In", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate(AuthRoutes.REGISTER) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Register", color = Color.White, fontSize = 16.sp)
            }
        }

        // ---------- PAGE INDICATORS ----------
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 110.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 8.dp else 6.dp)
                        .background(
                            if (pagerState.currentPage == index)
                                Color.White
                            else
                                Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }

        // ---------- FOOTER ----------
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                FooterLink("Internet Banking")
                FooterLink("Open Account")
                FooterLink("Help")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "LemonPay (Licensed by the Central Bank of Nigeria)",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun FooterLink(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 13.sp,
        modifier = Modifier.clickable { }
    )
}
