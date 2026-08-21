package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AccountEntity
import com.example.data.model.PlatformType
import com.example.ui.theme.SleekBorderDark
import com.example.ui.theme.SleekBorderLight

data class PlatformBadgeStyle(
    val tag: String,
    val platformName: String,
    val containerColor: Color,
    val contentColor: Color,
    val darkContainerColor: Color,
    val darkContentColor: Color
)

fun getPlatformBadgeStyle(platformType: PlatformType): PlatformBadgeStyle {
    return when (platformType) {
        PlatformType.YOUTUBE -> PlatformBadgeStyle(
            tag = "YT",
            platformName = "YouTube",
            containerColor = Color(0xFFFEF2F2), // red-50
            contentColor = Color(0xFFDC2626),   // red-600
            darkContainerColor = Color(0xFF450A0A),
            darkContentColor = Color(0xFFF87171)
        )
        PlatformType.INSTAGRAM -> PlatformBadgeStyle(
            tag = "IG",
            platformName = "Instagram",
            containerColor = Color(0xFFFDF2F8), // pink-50
            contentColor = Color(0xFFDB2777),   // pink-600
            darkContainerColor = Color(0xFF500724),
            darkContentColor = Color(0xFFF472B6)
        )
        PlatformType.TIKTOK -> PlatformBadgeStyle(
            tag = "TK",
            platformName = "TikTok",
            containerColor = Color(0xFF18181B), // zinc-900
            contentColor = Color.White,
            darkContainerColor = Color(0xFF27272A),
            darkContentColor = Color.White
        )
        PlatformType.FACEBOOK -> PlatformBadgeStyle(
            tag = "FB",
            platformName = "Facebook",
            containerColor = Color(0xFFEFF6FF), // blue-50
            contentColor = Color(0xFF2563EB),   // blue-600
            darkContainerColor = Color(0xFF172554),
            darkContentColor = Color(0xFF60A5FA)
        )
        PlatformType.WHATSAPP -> PlatformBadgeStyle(
            tag = "WA",
            platformName = "WhatsApp",
            containerColor = Color(0xFFF0FDF4), // green-50
            contentColor = Color(0xFF16A34A),   // green-600
            darkContainerColor = Color(0xFF052E16),
            darkContentColor = Color(0xFF4ADE80)
        )
        PlatformType.GITHUB -> PlatformBadgeStyle(
            tag = "GH",
            platformName = "GitHub",
            containerColor = Color(0xFF18181B), // zinc-900
            contentColor = Color.White,
            darkContainerColor = Color(0xFF27272A),
            darkContentColor = Color.White
        )
        PlatformType.THREADS -> PlatformBadgeStyle(
            tag = "TH",
            platformName = "Threads",
            containerColor = Color(0xFFF5F3FF), // violet-50
            contentColor = Color(0xFF7C3AED),
            darkContainerColor = Color(0xFF2E1065),
            darkContentColor = Color(0xFFA78BFA)
        )
        PlatformType.PINTEREST -> PlatformBadgeStyle(
            tag = "PT",
            platformName = "Pinterest",
            containerColor = Color(0xFFFFF1F2), // rose-50
            contentColor = Color(0xFFE11D48),
            darkContainerColor = Color(0xFF4C0519),
            darkContentColor = Color(0xFFFB7185)
        )
        PlatformType.FIVERR -> PlatformBadgeStyle(
            tag = "FV",
            platformName = "Fiverr",
            containerColor = Color(0xFFECFDF5), // emerald-50
            contentColor = Color(0xFF059669),
            darkContainerColor = Color(0xFF064E3B),
            darkContentColor = Color(0xFF34D399)
        )
        PlatformType.SUPERPROFILE -> PlatformBadgeStyle(
            tag = "SP",
            platformName = "SuperProfile",
            containerColor = Color(0xFFFFFBEB), // amber-50
            contentColor = Color(0xFFD97706),
            darkContainerColor = Color(0xFF451A03),
            darkContentColor = Color(0xFFFBBF24)
        )
        PlatformType.BLOGGER -> PlatformBadgeStyle(
            tag = "BL",
            platformName = "Blogger",
            containerColor = Color(0xFFFFF7ED), // orange-50
            contentColor = Color(0xFFEA580C),   // orange-600
            darkContainerColor = Color(0xFF431407),
            darkContentColor = Color(0xFFFB923C)
        )
        PlatformType.LINK_HUB -> PlatformBadgeStyle(
            tag = "LK",
            platformName = "LinkHub",
            containerColor = Color(0xFFF0F9FF), // sky-50
            contentColor = Color(0xFF0284C7),
            darkContainerColor = Color(0xFF082F49),
            darkContentColor = Color(0xFF38BDF8)
        )
        PlatformType.WEBSITE -> PlatformBadgeStyle(
            tag = "WB",
            platformName = "Website",
            containerColor = Color(0xFFF4F4F5), // zinc-100
            contentColor = Color(0xFF3F3F46),
            darkContainerColor = Color(0xFF27272A),
            darkContentColor = Color(0xFFE4E4E7)
        )
        PlatformType.OTHER -> PlatformBadgeStyle(
            tag = "OT",
            platformName = "Link",
            containerColor = Color(0xFFF4F4F5), // zinc-100
            contentColor = Color(0xFF3F3F46),
            darkContainerColor = Color(0xFF27272A),
            darkContentColor = Color(0xFFE4E4E7)
        )
    }
}

fun getPlatformIcon(platformType: PlatformType): ImageVector {
    return when (platformType) {
        PlatformType.YOUTUBE -> Icons.Filled.PlayCircle
        PlatformType.INSTAGRAM -> Icons.Filled.PhotoCamera
        PlatformType.TIKTOK -> Icons.Filled.MusicNote
        PlatformType.FACEBOOK -> Icons.Filled.ThumbUp
        PlatformType.WHATSAPP -> Icons.Filled.Chat
        PlatformType.THREADS -> Icons.Filled.AlternateEmail
        PlatformType.PINTEREST -> Icons.Filled.PushPin
        PlatformType.GITHUB -> Icons.Filled.Code
        PlatformType.FIVERR -> Icons.Filled.Work
        PlatformType.SUPERPROFILE -> Icons.Filled.Badge
        PlatformType.BLOGGER -> Icons.Filled.Article
        PlatformType.LINK_HUB -> Icons.Filled.Link
        PlatformType.WEBSITE -> Icons.Filled.Language
        PlatformType.OTHER -> Icons.Filled.OpenInNew
    }
}

@Composable
fun SleekAccountGridCard(
    account: AccountEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f
    val badgeStyle = getPlatformBadgeStyle(account.platformType)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardPressScale"
    )

    val cardBg by animateColorAsState(
        targetValue = if (isDark) Color(0xFF18181B) else Color.White,
        animationSpec = tween(200),
        label = "cardBg"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isDark) SleekBorderDark else SleekBorderLight,
        animationSpec = tween(200),
        label = "cardBorder"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = if (isDark) 0.dp else 1.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.6f)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(cardBg)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 10.dp, vertical = 9.dp)
            .testTag("account_item_${account.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Platform 2-Letter Badge (compact 30dp)
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(if (isDark) badgeStyle.darkContainerColor else badgeStyle.containerColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeStyle.tag,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    ),
                    color = if (isDark) badgeStyle.darkContentColor else badgeStyle.contentColor
                )
            }

            // Account Name & Platform Label
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (account.handle.isNotBlank() && account.handle != account.title) account.handle else account.title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.5.sp,
                        letterSpacing = (-0.2).sp
                    ),
                    color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = badgeStyle.platformName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 9.5.sp
                    ),
                    color = Color(0xFFA1A1AA),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun CategoryTabButton(
    categoryName: String,
    isSelected: Boolean,
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "tabPressScale"
    )

    val targetBgColor = if (isSelected) {
        if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
    } else {
        if (isDark) Color(0xFF27272A).copy(alpha = 0.6f) else Color(0xFFF4F4F5)
    }

    val targetContentColor = if (isSelected) {
        if (isDark) Color(0xFF18181B) else Color.White
    } else {
        if (isDark) Color(0xFFA1A1AA) else Color(0xFF71717A)
    }

    val targetBorderColor = if (isSelected) {
        Color.Transparent
    } else {
        if (isDark) SleekBorderDark else SleekBorderLight
    }

    val animatedBgColor by animateColorAsState(targetBgColor, animationSpec = tween(180), label = "tabBg")
    val animatedContentColor by animateColorAsState(targetContentColor, animationSpec = tween(180), label = "tabContent")
    val animatedBorderColor by animateColorAsState(targetBorderColor, animationSpec = tween(180), label = "tabBorder")

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(20.dp))
            .background(animatedBgColor)
            .border(width = 0.5.dp, color = animatedBorderColor, shape = RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("category_tab_$categoryName"),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 11.sp,
                    letterSpacing = 0.2.sp
                ),
                color = animatedContentColor
            )
            if (count > 0) {
                val badgeBg = if (isSelected) {
                    if (isDark) Color(0xFF18181B).copy(alpha = 0.15f) else Color.White.copy(alpha = 0.25f)
                } else {
                    if (isDark) Color(0xFF3F3F46) else Color(0xFFE4E4E7)
                }
                val animatedBadgeBg by animateColorAsState(badgeBg, animationSpec = tween(180), label = "badgeBg")

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(animatedBadgeBg)
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "$count",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = animatedContentColor
                    )
                }
            }
        }
    }
}

// Backward compatible aliases for Bento & other sections
@Composable
fun BentoSectionCard(
    title: String,
    icon: ImageVector,
    accounts: List<AccountEntity>,
    onOpenAccount: (AccountEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        accounts.forEach { account ->
            SleekAccountGridCard(
                account = account,
                onClick = { onOpenAccount(account) }
            )
        }
    }
}

@Composable
fun OtherLinksSectionCard(
    accounts: List<AccountEntity>,
    onOpenAccount: (AccountEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        accounts.forEach { account ->
            SleekAccountGridCard(
                account = account,
                onClick = { onOpenAccount(account) }
            )
        }
    }
}
