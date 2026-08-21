package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AccountCategory
import com.example.data.model.AccountEntity
import com.example.data.model.PortfolioConfigEntity
import com.example.ui.components.CategoryTabButton
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

    var isLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isLoaded = true
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (isLoaded) 1f else 0f,
        animationSpec = tween(220, easing = FastOutSlowInEasing),
        label = "homeFadeIn"
    )
    val contentOffsetY by animateFloatAsState(
        targetValue = if (isLoaded) 0f else 8f,
        animationSpec = tween(220, easing = FastOutSlowInEasing),
        label = "homeSlideIn"
    )

    val tagBadges = config.taglineBadges
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    // Categories definition
    val categories = AccountCategory.ALL
    var selectedCategory by remember { mutableStateOf(AccountCategory.SOCIAL_MEDIA) }

    // Calculate count per category
    val categoryCounts = remember(visibleAccounts) {
        categories.associateWith { cat ->
            visibleAccounts.count { AccountCategory.normalize(it.category, it.platformType) == cat }
        }
    }

    val cardBgColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFF18181B) else Color.White,
        animationSpec = tween(200),
        label = "cardBgColor"
    )
    val cardBorderColor by animateColorAsState(
        targetValue = if (isDark) SleekBorderDark else SleekBorderLight,
        animationSpec = tween(200),
        label = "cardBorderColor"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .graphicsLayer {
                alpha = contentAlpha
                translationY = contentOffsetY
            }
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            // 1. Sleek Profile Header (Avatar, Name, Subtitle, Badges)
            item(key = "profile_header") {
                Spacer(modifier = Modifier.height(4.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MaMonogramLogo(
                        size = 80.dp,
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
                                fontSize = 23.sp,
                                letterSpacing = (-0.5).sp
                            ),
                            color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                        )
                        Text(
                            text = config.brandName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.5.sp,
                                letterSpacing = 1.8.sp
                            ),
                            color = Color(0xFF71717A)
                        )
                    }

                    // Sleek Tag Badges (pill-shaped)
                    if (tagBadges.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            tagBadges.forEach { badge ->
                                val badgeBg by animateColorAsState(
                                    targetValue = if (isDark) Color(0xFF27272A) else Color(0xFFF4F4F5),
                                    animationSpec = tween(180),
                                    label = "badgeBg"
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(badgeBg)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = badge.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.5.sp,
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
                item(key = "about_card") {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (isDark) 0.dp else 1.dp,
                                shape = RoundedCornerShape(14.dp),
                                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.6f)
                            )
                            .border(
                                width = 1.dp,
                                color = cardBorderColor,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardBgColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = config.aboutTitle.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 9.5.sp,
                                    letterSpacing = 1.5.sp
                                ),
                                color = Color(0xFFA1A1AA)
                            )
                            Text(
                                text = config.aboutBio,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 12.5.sp,
                                    lineHeight = 19.sp
                                ),
                                color = if (isDark) Color(0xFFD4D4D8) else Color(0xFF52525B)
                            )
                        }
                    }
                }
            }

            // 3. Compact My Accounts Section with Category Buttons
            if (config.showAccounts && visibleAccounts.isNotEmpty()) {
                item(key = "accounts_section") {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "MY ACCOUNTS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.5.sp
                                ),
                                color = Color(0xFFA1A1AA)
                            )

                            Text(
                                text = "${visibleAccounts.size} total",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp
                                ),
                                color = Color(0xFFA1A1AA).copy(alpha = 0.7f)
                            )
                        }

                        // Category Buttons (Horizontal scrollable compact bar)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            categories.forEach { category ->
                                val count = categoryCounts[category] ?: 0
                                CategoryTabButton(
                                    categoryName = category,
                                    isSelected = category == selectedCategory,
                                    count = count,
                                    onClick = { selectedCategory = category }
                                )
                            }
                        }

                        // Animated Category Content Transition
                        AnimatedContent(
                            targetState = selectedCategory,
                            transitionSpec = {
                                (fadeIn(animationSpec = tween(180, easing = FastOutSlowInEasing)) +
                                        slideInVertically(animationSpec = tween(180, easing = FastOutSlowInEasing)) { it / 24 })
                                    .togetherWith(
                                        fadeOut(animationSpec = tween(120, easing = FastOutSlowInEasing))
                                    )
                            },
                            label = "CategoryAccountsTransition"
                        ) { targetCat ->
                            val currentCategoryAccounts = visibleAccounts.filter {
                                AccountCategory.normalize(it.category, it.platformType) == targetCat
                            }

                            if (currentCategoryAccounts.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(cardBgColor)
                                        .border(
                                            width = 1.dp,
                                            color = cardBorderColor,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(vertical = 24.dp, horizontal = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No accounts in $targetCat yet",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = Color(0xFFA1A1AA)
                                    )
                                }
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val chunkedAccounts = currentCategoryAccounts.chunked(2)
                                    chunkedAccounts.forEach { rowAccounts ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            rowAccounts.forEach { account ->
                                                SleekAccountGridCard(
                                                    account = account,
                                                    onClick = { IntentUtils.openUrl(context, account.url) },
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            if (rowAccounts.size == 1) {
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Sleek "Get in Touch" Bottom Action Card
            if (config.showContact && config.email.isNotBlank()) {
                item(key = "contact_card") {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (isDark) 0.dp else 1.dp,
                                shape = RoundedCornerShape(14.dp),
                                spotColor = Color(0xFFE4E4E7).copy(alpha = 0.6f)
                            )
                            .border(
                                width = 1.dp,
                                color = cardBorderColor,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardBgColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                            fontSize = 9.5.sp,
                                            letterSpacing = 1.5.sp
                                        ),
                                        color = Color(0xFFA1A1AA)
                                    )
                                    Text(
                                        text = config.email,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.5.sp
                                        ),
                                        color = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B)
                                    )
                                }

                                val iconBg by animateColorAsState(
                                    targetValue = if (isDark) Color(0xFF27272A) else Color(0xFFF4F4F5),
                                    animationSpec = tween(180),
                                    label = "iconBg"
                                )

                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(iconBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Email,
                                        contentDescription = "Email",
                                        tint = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }

                            val emailButtonInteraction = remember { MutableInteractionSource() }
                            val emailButtonPressed by emailButtonInteraction.collectIsPressedAsState()
                            val emailBtnScale by animateFloatAsState(
                                targetValue = if (emailButtonPressed) 0.98f else 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium
                                ),
                                label = "emailBtnScale"
                            )

                            val emailBtnBg by animateColorAsState(
                                targetValue = if (isDark) Color(0xFFFAFAFA) else Color(0xFF18181B),
                                animationSpec = tween(180),
                                label = "emailBtnBg"
                            )
                            val emailBtnContent by animateColorAsState(
                                targetValue = if (isDark) Color(0xFF18181B) else Color.White,
                                animationSpec = tween(180),
                                label = "emailBtnContent"
                            )

                            // Full width sleek button
                            Button(
                                onClick = { IntentUtils.sendEmail(context, config.email) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = emailBtnBg,
                                    contentColor = emailBtnContent
                                ),
                                interactionSource = emailButtonInteraction,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .graphicsLayer {
                                        scaleX = emailBtnScale
                                        scaleY = emailBtnScale
                                    }
                                    .testTag("send_email_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Text(
                                        text = "Get in Touch",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 5. Minimalist Home Indicator Bar & Admin Footer
            item(key = "footer_admin") {
                val adminLinkInteraction = remember { MutableInteractionSource() }
                val adminLinkPressed by adminLinkInteraction.collectIsPressedAsState()
                val adminLinkScale by animateFloatAsState(
                    targetValue = if (adminLinkPressed) 0.95f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "adminLinkScale"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Sleek Home Indicator Bar
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (isDark) Color(0xFF3F3F46) else Color(0xFFE4E4E7))
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = adminLinkScale
                                scaleY = adminLinkScale
                            }
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(
                                interactionSource = adminLinkInteraction,
                                indication = null,
                                onClick = onOpenAdmin
                            )
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
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp),
                        color = Color(0xFFA1A1AA).copy(alpha = 0.5f)
                    )
                }
            }
        }
    )
}
