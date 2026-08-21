package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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

    Box(
        modifier = modifier
            .shadow(
                elevation = if (isDark) 0.dp else 1.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.6f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(if (isDark) Color(0xFF18181B) else Color.White)
            .border(
                width = 1.dp,
                color = if (isDark) SleekBorderDark else SleekBorderLight,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
            .testTag("account_item_${account.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Platform 2-Letter Badge
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDark) badgeStyle.darkContainerColor else badgeStyle.containerColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeStyle.tag,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
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
                        fontSize = 10.sp
                    ),
                    color = Color(0xFFA1A1AA),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
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
