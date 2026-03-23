package com.example.lemon.ui.toast



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CenteredAnimatedToast(
    toast: UiToast,
    visible: Boolean
) {
    if (!visible) return

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(20.dp)
                .background(Color.Black.copy(alpha = 0.60f))
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ToastCard(toast)
        }
    }
}

@Composable
private fun ToastCard(toast: UiToast){
    val icon = when (toast) {
        is UiToast.Success -> Icons.Default.CheckCircle
        is UiToast.Error -> Icons.Default.Close
    }

    Surface(
        shape = RoundedCornerShape(15.dp),
        color = Color(0xFFFFEB3B),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 20.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = when (toast) {
                    is UiToast.Success -> toast.message
                    is UiToast.Error -> toast.message
                },
                color = Color.Black
            )
        }
    }
}