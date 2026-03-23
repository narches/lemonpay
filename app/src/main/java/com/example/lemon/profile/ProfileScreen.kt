package com.example.lemon.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lemon.profile.model.UserProfileUiModel

/* -------------------- THEME COLORS -------------------- */

private val LemonBlack = Color(0xFF000000)
private val LemonCard = Color(0xFF1C1C1C)
private val LemonYellow = Color(0xFFF4CE14)
private val LemonGray = Color(0xFF9E9E9E)

/* -------------------- SCREEN -------------------- */

@Composable
fun ProfileScreen(
    profile: UserProfileUiModel,
    onBack: () -> Unit,
    onLogout: () -> Unit,

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonBlack)
    ) {
        ProfileTopBar(onBack)

        Spacer(Modifier.height(16.dp))

        ProfileHeaderCard(profile)

        Spacer(Modifier.height(20.dp))

        ProfileInfoCard(
            fullName = profile.name,
            phone = profile.phone,
            email = profile.email
        )

        Spacer(Modifier.weight(1f))

        LogoutButton(onLogout)

        Spacer(Modifier.height(32.dp))
    }
}

/* -------------------- TOP BAR -------------------- */

@Composable
private fun ProfileTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Text(
            text = "My Profile",
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

/* -------------------- HEADER CARD -------------------- */

@Composable
private fun ProfileHeaderCard(profile: UserProfileUiModel) {
    Card(
        backgroundColor = LemonCard,
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(LemonGray)
            )

            Spacer(Modifier.height(12.dp))

            Text(profile.name, color = LemonYellow)

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Account Number", color = LemonGray)
                Spacer(Modifier.weight(1f))
                Text(profile.accountNumber, color = Color.White)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.ContentCopy, null, tint = LemonGray)
            }
        }
    }
}

/* -------------------- INFO CARD -------------------- */

@Composable
private fun ProfileInfoCard(
    fullName: String,
    phone: String,
    email: String,
) {
    Card(
        backgroundColor = LemonCard,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column {

            Spacer(Modifier.height(10.dp))
            ProfileRow("Full Name", fullName)
            Spacer(Modifier.height(5.dp))
            Divider(color = Color.DarkGray)
            Spacer(Modifier.height(5.dp))
            ProfileRow("Mobile Number", phone, )
            Spacer(Modifier.height(5.dp))
            Divider(color = Color.DarkGray)
            Spacer(Modifier.height(5.dp))
            ProfileRow("Email", email, )
        }
    }
}

/* -------------------- ROW -------------------- */

@Composable
private fun ProfileRow(
    label: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = LemonGray)
        Spacer(Modifier.weight(1f))
        Text(value, color = Color.White)

        if (onClick != null) {
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Default.ChevronRight, null, tint = LemonGray)
        }
    }
}

/* -------------------- LOGOUT -------------------- */

@Composable
private fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = LemonYellow,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(50.dp)
    ) {
        Text("Logout")
    }
}