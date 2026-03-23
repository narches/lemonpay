

package com.example.lemon

import android.view.View
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.lemon.navigation.Routes
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndexScreen(
    navController: NavController,
) {
    val images = listOf(
        R.drawable.lab,
        R.drawable.woman,
        R.drawable.shoppin

    )
    val LemonYellow = Color(0xFFF4CE14)
    val LemonBlack = Color(0xFF111111)


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
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(WindowInsets.systemBars)
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
                            LemonBlack.copy(alpha = 0.2f),
                            LemonBlack.copy(alpha = 0.55f)
                        )
                    )
                )
        )


        // ---------- CENTER CONTENT ----------
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "LemonPay",
                color = LemonYellow,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Secure, fast, and personalized banking right from your mobile device.",
                color = LemonYellow,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(Routes.LOGIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LemonBlack,
                    contentColor = LemonYellow
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Log In", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(

                onClick = { navController.navigate(Routes.REGISTER) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = LemonBlack,
                    contentColor = LemonYellow
                ),
                border = BorderStroke(1.dp, LemonBlack),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Register", color = LemonYellow, fontSize = 16.sp)
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
                                LemonYellow
                            else
                                LemonYellow.copy(alpha = 0.5f),
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
                color = LemonYellow.copy(alpha = 0.8f),
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

