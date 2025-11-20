
package com.example.lemon



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.lemon.database.AppDatabase
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import com.example.lemon.database.MenuItemRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    // Build DB once (dev-friendly: use fallbackToDestructiveMigration to avoid migration crashes)
    val db = remember {
        Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration() // remove in production or add migrations
            .build()
    }

    // Observe LiveData from Room
    val menuItems by db.menuItemDao().getAll().observeAsState(emptyList())

    // Preload DB with default Dishes on first run (safely in coroutine)
    LaunchedEffect(Unit) {
        // countItems is suspend
        val cnt = withContext(Dispatchers.IO) { db.menuItemDao().countItems() }
        if (cnt == 0) {
            val defaultItems = Dishes.map {
                MenuItemRoom(
                    name = it.name,
                    description = it.description,
                    price = it.price.removePrefix("$").toDouble(),
                    imageResId = it.image
                )
            }
            withContext(Dispatchers.IO) {
                db.menuItemDao().insertAll(*defaultItems.toTypedArray())
            }
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = if (searchQuery.isBlank()) menuItems else menuItems.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(Modifier.fillMaxSize()) {
        // Upper panel ...
        Column(modifier = Modifier.background(Color(0xFF495E57)).padding(12.dp)) {
            Text(text = stringResource(id = R.string.title), fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF4CE14))
            Text(text = stringResource(id = R.string.location), fontSize = 24.sp, color = Color(0xFFEDEFEE))
            Row(modifier = Modifier.padding(top = 18.dp)) {
                Text(text = stringResource(id = R.string.description), color = Color(0xFFEDEFEE), fontSize = 18.sp, modifier = Modifier.fillMaxWidth(0.6f).padding(bottom = 28.dp))
                Image(painter = painterResource(id = R.drawable.saus), contentDescription = "", modifier = Modifier.clip(RoundedCornerShape(20.dp)))
            }
            // Use reusable SearchBar
            SearchBar(query = searchQuery, onQueryChange = { searchQuery = it }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        }
        WeeklySpecialCard()
        // List
        LazyColumn {
            items(filteredItems) { item ->
                MenuDishRoom(item, navController)
            }
        }
    }
}





@Composable
fun WeeklySpecialCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Weekly Special - Order for Delivery!",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

