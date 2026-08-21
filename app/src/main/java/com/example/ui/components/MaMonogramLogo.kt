package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.SleekBorderDark
import com.example.ui.theme.SleekBorderLight

@Composable
fun MaMonogramLogo(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    customAvatarUri: String? = null,
    isCircular: Boolean = false,
    showGlow: Boolean = true
) {
    // Sleek Interface squircle rounded-3xl (or circle if specified)
    val shape = if (isCircular) CircleShape else RoundedCornerShape(24.dp)
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f // Check if dark mode

    Box(
        modifier = modifier
            .size(size)
            .then(
                if (showGlow) {
                    Modifier.shadow(
                        elevation = if (isDark) 4.dp else 12.dp,
                        shape = shape,
                        spotColor = if (isDark) Color.Black.copy(alpha = 0.6f) else Color(0xFFE4E4E7).copy(alpha = 0.9f),
                        ambientColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color(0xFFE4E4E7).copy(alpha = 0.5f)
                    )
                } else Modifier
            )
            .border(
                width = 1.dp,
                color = if (isDark) SleekBorderDark else SleekBorderLight,
                shape = shape
            )
            .background(
                color = if (isDark) Color(0xFF18181B) else Color(0xFF18181B), // zinc-900
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!customAvatarUri.isNullOrBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(customAvatarUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Sleek Monogram Typography "MA"
            Text(
                text = "MA",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = (size.value * 0.38f).sp,
                    letterSpacing = (-1.5).sp
                ),
                color = Color.White
            )
        }
    }
}
