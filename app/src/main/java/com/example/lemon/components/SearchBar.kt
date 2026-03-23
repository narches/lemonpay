


package com.example.lemon.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lemon.R


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {


    //Give Space
    Spacer(modifier = Modifier.padding(top = 20.dp))
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp),
        placeholder = {
            Text("Search by amount, date or account")
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search transactions",
                tint = Color(0xFFFFEB3B)
            )
        },
        shape = RoundedCornerShape(25.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFFFEB3B),
            unfocusedBorderColor = Color(0xFFFFEB3B),
            textColor = Color(0xFFFFEB3B),
            placeholderColor = Color(0xFFFFEB3B),
            backgroundColor = Color(0xFF000000),
        )
    )
}
