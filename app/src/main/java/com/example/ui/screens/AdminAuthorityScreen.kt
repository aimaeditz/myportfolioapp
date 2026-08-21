package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.AdminTab
import com.example.ui.viewmodel.PortfolioViewModel

data class AdminControlItem(
    val tab: AdminTab,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun AdminAuthorityScreen(
    viewModel: PortfolioViewModel,
    onBackToHome: () -> Unit
) {
    val activeTab by viewModel.activeAdminTab.collectAsState()
    val config by viewModel.config.collectAsState()
    val allAccounts by viewModel.allAccounts.collectAsState()

    val controlItems = listOf(
        AdminControlItem(
            tab = AdminTab.PROFILE,
            title = "Profile",
            description = "Edit creator name, brand, badges & email",
            icon = Icons.Filled.Person
        ),
        AdminControlItem(
            tab = AdminTab.ABOUT,
            title = "About",
            description = "Edit personal biography statement",
            icon = Icons.Filled.Info
        ),
        AdminControlItem(
            tab = AdminTab.ACCOUNTS,
            title = "Accounts",
            description = "Add, edit, reorder & hide online accounts",
            icon = Icons.Filled.AccountBalanceWallet
        ),
        AdminControlItem(
            tab = AdminTab.APPEARANCE,
            title = "Appearance",
            description = "Theme mode & accent colors",
            icon = Icons.Filled.Palette
        ),
        AdminControlItem(
            tab = AdminTab.MEDIA,
            title = "Media & Logo",
            description = "Custom profile photo or MA Monogram",
            icon = Icons.Filled.PermMedia
        ),
        AdminControlItem(
            tab = AdminTab.SETTINGS,
            title = "App Settings",
            description = "App name, splash & section visibility",
            icon = Icons.Filled.Settings
        ),
        AdminControlItem(
            tab = AdminTab.SECURITY,
            title = "Security",
            description = "Change private admin unlock password",
            icon = Icons.Filled.Security
        ),
        AdminControlItem(
            tab = AdminTab.BACKUP,
            title = "Backup & Restore",
            description = "Export JSON, restore & reset defaults",
            icon = Icons.Filled.Backup
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 24.dp)
    ) {
        when (activeTab) {
            AdminTab.DASHBOARD -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            IconButton(
                                onClick = onBackToHome,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to Home",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Column {
                                Text(
                                    text = "ADMIN AUTHORITY",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    ),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                                Text(
                                    text = "Private Control Center",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                viewModel.logoutAdmin()
                                onBackToHome()
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Exit Admin Mode",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Text(
                        text = "Manage your digital presence with absolute precision. All controls dictate global system state in real time.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Controls Grid / List
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        controlItems.forEach { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { viewModel.navigateAdminTab(item.tab) },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(42.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                                                .border(
                                                    0.5.dp,
                                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                                                    RoundedCornerShape(10.dp)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.title,
                                                tint = MaterialTheme.colorScheme.tertiary,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                        Column {
                                            Text(
                                                text = item.title,
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = item.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = "Open",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Exit Admin Mode Button
                    OutlinedButton(
                        onClick = {
                            viewModel.logoutAdmin()
                            onBackToHome()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Exit Admin Authority", fontWeight = FontWeight.Bold)
                    }
                }
            }

            AdminTab.PROFILE -> {
                AdminProfileScreen(
                    config = config,
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.ABOUT -> {
                AdminAboutScreen(
                    config = config,
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.ACCOUNTS -> {
                AdminAccountsScreen(
                    accounts = allAccounts,
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.APPEARANCE -> {
                AdminAppearanceScreen(
                    config = config,
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.MEDIA -> {
                AdminMediaScreen(
                    config = config,
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.SETTINGS -> {
                AdminAppSettingsScreen(
                    config = config,
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.SECURITY -> {
                AdminSecurityScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }

            AdminTab.BACKUP -> {
                AdminBackupScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.navigateAdminTab(AdminTab.DASHBOARD) }
                )
            }
        }
    }
}
