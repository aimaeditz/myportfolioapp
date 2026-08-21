package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.AccountEditDialog
import com.example.ui.screens.AdminAuthorityScreen
import com.example.ui.screens.AdminLoginDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MyPortfolioTheme
import com.example.ui.viewmodel.AdminTab
import com.example.ui.viewmodel.PortfolioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: PortfolioViewModel = viewModel()
            val config by viewModel.config.collectAsState()
            val visibleAccounts by viewModel.visibleAccounts.collectAsState()
            val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
            val isSplashShowing by viewModel.isSplashShowing.collectAsState()
            val showAdminLoginDialog by viewModel.showAdminLoginDialog.collectAsState()
            val showAccountEditDialog by viewModel.showAccountEditDialog.collectAsState()
            val editingAccount by viewModel.editingAccount.collectAsState()
            val statusMessage by viewModel.statusMessage.collectAsState()
            val activeAdminTab by viewModel.activeAdminTab.collectAsState()

            var isInAdminScreen by remember { mutableStateOf(false) }
            val snackbarHostState = remember { SnackbarHostState() }

            // Back button handling on mobile
            BackHandler(enabled = showAccountEditDialog || showAdminLoginDialog || isInAdminScreen) {
                when {
                    showAccountEditDialog -> viewModel.dismissAccountEditDialog()
                    showAdminLoginDialog -> viewModel.closeAdminLoginDialog()
                    isInAdminScreen -> {
                        if (activeAdminTab != AdminTab.DASHBOARD) {
                            viewModel.navigateAdminTab(AdminTab.DASHBOARD)
                        } else {
                            isInAdminScreen = false
                        }
                    }
                }
            }

            LaunchedEffect(statusMessage) {
                statusMessage?.let { msg ->
                    snackbarHostState.showSnackbar(msg)
                    viewModel.clearStatusMessage()
                }
            }

            MyPortfolioTheme(
                themeMode = config.themeMode,
                accentHex = config.accentHex
            ) {
                AnimatedContent(
                    targetState = config.splashEnabled && isSplashShowing,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(220, easing = FastOutSlowInEasing)) togetherWith
                                fadeOut(animationSpec = tween(220, easing = FastOutSlowInEasing))
                    },
                    label = "SplashAppTransition"
                ) { showingSplash ->
                    if (showingSplash) {
                        SplashScreen(
                            appName = config.appName,
                            creatorName = config.creatorName,
                            brandName = config.brandName,
                            customAvatarUri = config.customAvatarUri,
                            onFinish = { viewModel.dismissSplash() }
                        )
                    } else {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                if (!isInAdminScreen) {
                                    TopHeaderBar(
                                        appName = config.appName,
                                        customAvatarUri = config.customAvatarUri,
                                        isAdminLoggedIn = isAdminLoggedIn,
                                        currentThemeMode = config.themeMode,
                                        onThemeSelect = { mode -> viewModel.updateThemeMode(mode) },
                                        onAvatarClick = {},
                                        onAdminClick = {
                                            if (isAdminLoggedIn) {
                                                isInAdminScreen = true
                                            } else {
                                                viewModel.openAdmin()
                                            }
                                        }
                                    )
                                }
                            },
                            snackbarHost = { SnackbarHost(snackbarHostState) }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                AnimatedContent(
                                    targetState = isInAdminScreen && isAdminLoggedIn,
                                    transitionSpec = {
                                        (fadeIn(animationSpec = tween(200, easing = FastOutSlowInEasing)) +
                                                scaleIn(initialScale = 0.98f, animationSpec = tween(200, easing = FastOutSlowInEasing)))
                                            .togetherWith(
                                                fadeOut(animationSpec = tween(160, easing = FastOutSlowInEasing)) +
                                                        scaleOut(targetScale = 0.98f, animationSpec = tween(160, easing = FastOutSlowInEasing))
                                            )
                                    },
                                    label = "ScreenTransition"
                                ) { inAdmin ->
                                    if (inAdmin) {
                                        AdminAuthorityScreen(
                                            viewModel = viewModel,
                                            onBackToHome = { isInAdminScreen = false }
                                        )
                                    } else {
                                        HomeScreen(
                                            config = config,
                                            visibleAccounts = visibleAccounts,
                                            onOpenAdmin = {
                                                if (isAdminLoggedIn) {
                                                    isInAdminScreen = true
                                                } else {
                                                    viewModel.openAdmin()
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // Admin Login Dialog
                            if (showAdminLoginDialog) {
                                AdminLoginDialog(
                                    onDismiss = { viewModel.closeAdminLoginDialog() },
                                    onLogin = { pass ->
                                        val success = viewModel.tryAdminLogin(pass)
                                        if (success) {
                                            isInAdminScreen = true
                                        }
                                        success
                                    }
                                )
                            }

                            // Account Add/Edit Dialog
                            if (showAccountEditDialog) {
                                AccountEditDialog(
                                    account = editingAccount,
                                    onDismiss = { viewModel.dismissAccountEditDialog() },
                                    onSave = { accountToSave ->
                                        viewModel.saveAccount(accountToSave)
                                    },
                                    onDelete = { accountToDelete ->
                                        viewModel.deleteAccount(accountToDelete)
                                        viewModel.dismissAccountEditDialog()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
