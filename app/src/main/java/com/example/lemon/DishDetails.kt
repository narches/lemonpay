package com.example.lemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DishDetails(dish: Dish) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Image(
            painter = painterResource(id = dish.image), // assuming imageRes is an Int drawable resource
            contentDescription = dish.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .height(300.dp)
                .width(800.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        // Dish Name
        Text(
            text = dish.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        // Dish Description
        Text(
            text = dish.description,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
        )

        // Dish Price
        Text(
            text = "${dish.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        // Counter for quantity
        Counter()

        // Add to Cart Button
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF4CE14),
                    contentColor = Color(0xFF05420E)
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            // optional ripple
            interactionSource = remember { MutableInteractionSource() },
        ) {
            Text(text = "Add to Cart", fontSize = 18.sp)
        }
    }
}


@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                counter--
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.h5
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.h5
            )
        }
    }
}






@Preview(showBackground = true)
@Composable
fun DishDetailsPreview() {
    DishDetails(
        dish = Dish(
            "Greek Salad",
            "$12.99",
            "The famous Greek salad...",
            R.drawable.greek
        )
    )
}
