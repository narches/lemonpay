
package com.example.lemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.lemon.datastore.DataStoreManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp



@Composable
fun MyBar(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    dataStoreManager: DataStoreManager,
    onProfileClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val user by dataStoreManager.userFlow.collectAsState(initial = null)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
            Image(
                painter = painterResource(id = R.drawable.ic_hamburger_menu),
                contentDescription = "Menu Icon",
                modifier = Modifier.size(30.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logox),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth(0.5F).padding(horizontal = 20.dp)
        )

        // If user registered and has imageUri show it; else show basket
        if (user?.isRegistered == true && user?.imageUri?.isNotBlank() == true) {
            val uri = user?.imageUri
            Box(modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() }) {
                AsyncImage(
                    model = uri,
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            IconButton(onClick = { /* cart */ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_basket),
                    contentDescription = "Cart",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}



@Composable
fun DrawerContent(
    modifier: Modifier = Modifier.alpha(0.5f),
    onClose: () -> Unit,
    onItemClick: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        // Close button
        Text(
            text = "X", color = Color(0xFFF4CE14),
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .clickable { onClose() }
        )

        Spacer(modifier = Modifier.height(100.dp))

        // Drawer Items
        DrawerTextItem("Home") { onItemClick("Home") }
        DrawerTextItem("About") { onItemClick("About") }
        DrawerTextItem("Menu") { onItemClick("Menu") }
        DrawerTextItem("Reservations") { onItemClick("Company") }
        DrawerTextItem("Orders") { onItemClick("Blog") }

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF495E57)),
        ) {
            Text("Login", color = Color(0xFFF4CE14),  fontSize = 20.sp)
        }
    }
}

@Composable
fun DrawerTextItem(text: String, onClick: () -> Unit) {
    Text(
        color = Color(0xFF495E57),
        text = text,
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .clickable { onClick() }
    )
}







@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
//    MyBar()
}

