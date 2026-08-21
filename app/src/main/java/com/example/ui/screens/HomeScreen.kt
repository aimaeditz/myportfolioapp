package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AccountEntity
import com.example.data.model.PortfolioConfigEntity
import com.example.ui.components.MaMonogramLogo
import com.example.ui.components.SleekAccountGridCard
import com.example.ui.theme.SleekBorderDark
import com.example.ui.theme.SleekBorderLight
import com.example.util.IntentUtils

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    config: PortfolioConfigEntity,
    visibleAccounts: List<AccountEntity>,
    onOpenAdmin: () -> Unit
) {
    val context = LocalContext.current
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f

    val tagBadges = config.taglineBadges
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            // 1. Sleek Profile Header (Avatar, Name, Subtitle, Badges)
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MaMonogramLogo(
                        size = 84.dp,
                        customAvatarUri = config.customAvatarUri,
                        showGlow = true
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = config.creatorName,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                letterSpacing = (-0.5).sp
                            ),
                            color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                        )
                        Text(
                            text = config.brandName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                letterSpacing = 2.sp
                            ),
                            color = Color(0xFF71717A)
                        )
                    }

                    // Sleek Tag Badges (pill-shaped)
                    if (tagBadges.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            tagBadges.forEach { badge ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isDark) Color(0xFF27272A) else Color(0xFFF4F4F5))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = badge.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            letterSpacing = 0.5.sp
                                        ),
                                        color = if (isDark) Color(0xFFA1A1AA) else Color(0xFF52525B)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. Sleek About Me Card
            if (config.showAbout && config.aboutBio.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (isDark) 0.dp else 1.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.6f)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isDark) SleekBorderDark else SleekBorderLight,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDark) Color(0xFF18181B) else Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = config.aboutTitle.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.5.sp
                                ),
                                color = Color(0xFFA1A1AA)
                            )
                            Text(
                                text = config.aboutBio,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp
                                ),
                                color = if (isDark) Color(0xFFD4D4D8) else Color(0xFF52525B)
                            )
                        }
                    }
                }
            }

            // 3. Sleek Accounts Bento Grid (2-Column Grid)
            if (config.showAccounts && visibleAccounts.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "MY ACCOUNTS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp,
                                letterSpacing = 1.5.sp
                            ),
                            color = Color(0xFFA1A1AA),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        )

                        // 2-Column Grid Layout
                        val chunkedAccounts = visibleAccounts.chunked(2)
                        chunkedAccounts.forEach { rowAccounts ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                rowAccounts.forEach { account ->
                                    SleekAccountGridCard(
                                        account = account,
                                        onClick = { IntentUtils.openUrl(context, account.url) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                // If row has only 1 item, fill remaining space
                                if (rowAccounts.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            // 4. Sleek "Get in Touch" Bottom Action Card
            if (config.showContact && config.email.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (isDark) 0.dp else 1.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.6f)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isDark) SleekBorderDark else SleekBorderLight,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDark) Color(0xFF18181B) else Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = config.contactTitle.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            fontSize = 10.sp,
                                            letterSpacing = 1.5.sp
                                        ),
                                        color = Color(0xFFA1A1AA)
                                    )
                                    Text(
                                        text = config.email,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 12.sp
                                        ),
                                        color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isDark) Color(0xFF27272A) else Color(0xFFF4F4F5)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Email,
                                        contentDescription = "Email",
                                        tint = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            // Full width sleek black/zinc button
                            Button(
                                onClick = { IntentUtils.sendEmail(context, config.email) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                    contentColor = if (isDark) Color(0xFF18181B) else Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("send_email_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Get in Touch",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 5. Minimalist Home Indicator Bar & Admin Footer
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Sleek Home Indicator Bar
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (isDark) Color(0xFF3F3F46) else Color(0xFFE4E4E7))
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(onClick = onOpenAdmin)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Admin Access",
                            tint = Color(0xFFA1A1AA).copy(alpha = 0.6f),
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "Admin Authority",
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 1.sp,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = Color(0xFFA1A1AA)
                        )
                    }

                    Text(
                        text = "© ${config.brandName} • All Rights Reserved",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = Color(0xFFA1A1AA).copy(alpha = 0.5f)
                    )
                }
            }
        }
    )
}
