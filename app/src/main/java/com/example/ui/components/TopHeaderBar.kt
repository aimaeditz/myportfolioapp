package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SleekBorderDark
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekEmerald

@Composable
fun TopHeaderBar(
    modifier: Modifier = Modifier,
    appName: String = "My Portfolio",
    customAvatarUri: String? = null,
    isAdminLoggedIn: Boolean = false,
    currentThemeMode: String = "LIGHT",
    onThemeSelect: (String) -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onAdminClick: () -> Unit = {}
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f
    var showThemeMenu by remember { mutableStateOf(false) }

    val barBg by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.background,
        animationSpec = tween(200),
        label = "barBg"
    )
    val buttonBg by animateColorAsState(
        targetValue = if (isDark) Color(0xFF18181B) else Color.White,
        animationSpec = tween(200),
        label = "buttonBg"
    )
    val buttonBorder by animateColorAsState(
        targetValue = if (isDark) SleekBorderDark else SleekBorderLight,
        animationSpec = tween(200),
        label = "buttonBorder"
    )

    // Pulse animation for online indicator
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(barBg)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Online indicator with green dot
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = onAvatarClick)
                    .padding(vertical = 4.dp, horizontal = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(SleekEmerald.copy(alpha = pulseAlpha))
                )
                Text(
                    text = "ONLINE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 10.sp
                    ),
                    color = if (isDark) Color(0xFFA1A1AA) else Color(0xFF71717A)
                )
            }

            // Right Actions: Theme Selector + Admin Settings
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val themeInteraction = remember { MutableInteractionSource() }
                val isThemePressed by themeInteraction.collectIsPressedAsState()
                val themeScale by animateFloatAsState(
                    targetValue = if (isThemePressed) 0.92f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "themeScale"
                )

                // Theme Toggle Icon & Compact Dropdown
                Box {
                    val themeIcon = when (currentThemeMode.uppercase()) {
                        "DARK" -> Icons.Filled.DarkMode
                        "SYSTEM" -> Icons.Filled.BrightnessAuto
                        else -> Icons.Filled.WbSunny
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .graphicsLayer {
                                scaleX = themeScale
                                scaleY = themeScale
                            }
                            .shadow(
                                elevation = if (isDark) 0.dp else 2.dp,
                                shape = CircleShape,
                                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.7f)
                            )
                            .clip(CircleShape)
                            .background(buttonBg)
                            .border(
                                width = 1.dp,
                                color = buttonBorder,
                                shape = CircleShape
                            )
                            .clickable(
                                interactionSource = themeInteraction,
                                indication = null,
                                onClick = { showThemeMenu = true }
                            )
                            .testTag("theme_switcher_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = themeIcon,
                            contentDescription = "Switch Theme (Current: $currentThemeMode)",
                            tint = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showThemeMenu,
                        onDismissRequest = { showThemeMenu = false },
                        modifier = Modifier
                            .background(buttonBg)
                            .border(
                                width = 1.dp,
                                color = buttonBorder,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        // Light
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.WbSunny,
                                        contentDescription = null,
                                        tint = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Light",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (currentThemeMode.equals("LIGHT", true)) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                                    )
                                    if (currentThemeMode.equals("LIGHT", true)) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                onThemeSelect("LIGHT")
                                showThemeMenu = false
                            },
                            modifier = Modifier.testTag("theme_option_light")
                        )

                        // Dark
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DarkMode,
                                        contentDescription = null,
                                        tint = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Dark",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (currentThemeMode.equals("DARK", true)) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                                    )
                                    if (currentThemeMode.equals("DARK", true)) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                onThemeSelect("DARK")
                                showThemeMenu = false
                            },
                            modifier = Modifier.testTag("theme_option_dark")
                        )

                        // System
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.BrightnessAuto,
                                        contentDescription = null,
                                        tint = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "System",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (currentThemeMode.equals("SYSTEM", true)) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                                    )
                                    if (currentThemeMode.equals("SYSTEM", true)) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                onThemeSelect("SYSTEM")
                                showThemeMenu = false
                            },
                            modifier = Modifier.testTag("theme_option_system")
                        )
                    }
                }

                val adminInteraction = remember { MutableInteractionSource() }
                val isAdminPressed by adminInteraction.collectIsPressedAsState()
                val adminScale by animateFloatAsState(
                    targetValue = if (isAdminPressed) 0.92f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "adminScale"
                )

                // Settings / Admin Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .graphicsLayer {
                            scaleX = adminScale
                            scaleY = adminScale
                        }
                        .shadow(
                            elevation = if (isDark) 0.dp else 2.dp,
                            shape = CircleShape,
                            spotColor = Color(0xFFE4E4E7).copy(alpha = 0.7f)
                        )
                        .clip(CircleShape)
                        .background(buttonBg)
                        .border(
                            width = 1.dp,
                            color = buttonBorder,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = adminInteraction,
                            indication = null,
                            onClick = onAdminClick
                        )
                        .testTag("admin_settings_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isAdminLoggedIn) Icons.Filled.AdminPanelSettings else Icons.Outlined.Settings,
                        contentDescription = if (isAdminLoggedIn) "Admin Dashboard" else "Admin Settings",
                        tint = if (isAdminLoggedIn) SleekEmerald else if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
