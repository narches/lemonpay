package com.example.lemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemon.database.MenuItemRoom
import androidx.navigation.NavController


@Composable
fun MenuDishRoom(item: MenuItemRoom, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, fontSize = 18.sp)
                Text(text = item.description, modifier = Modifier.padding(vertical = 5.dp))
                Text(text = "$${item.price}")
            }

            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        navController.navigate("DishDetails/${item.name}")
                    }
            )
        }
    }
}
