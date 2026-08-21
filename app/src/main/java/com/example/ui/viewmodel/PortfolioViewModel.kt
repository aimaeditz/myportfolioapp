package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.DefaultData
import com.example.data.model.AccountEntity
import com.example.data.model.PortfolioConfigEntity
import com.example.data.repository.PortfolioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AdminTab(val title: String, val iconName: String) {
    DASHBOARD("Admin Authority", "admin_panel_settings"),
    PROFILE("Profile", "person"),
    ABOUT("About", "info"),
    ACCOUNTS("Accounts", "account_balance_wallet"),
    APPEARANCE("Appearance", "palette"),
    MEDIA("Media & Logo", "perm_media"),
    SETTINGS("App Settings", "settings"),
    SECURITY("Security", "shield"),
    BACKUP("Backup & Restore", "backup")
}

class PortfolioViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val repository = PortfolioRepository(database.accountDao(), database.configDao())

    val config: StateFlow<PortfolioConfigEntity> = repository.config.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DefaultData.getDefaultConfig()
    )

    val visibleAccounts: StateFlow<List<AccountEntity>> = repository.visibleAccounts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DefaultData.getDefaultAccounts()
    )

    val allAccounts: StateFlow<List<AccountEntity>> = repository.allAccounts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DefaultData.getDefaultAccounts()
    )

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _isSplashShowing = MutableStateFlow(true)
    val isSplashShowing: StateFlow<Boolean> = _isSplashShowing.asStateFlow()

    private val _activeAdminTab = MutableStateFlow(AdminTab.DASHBOARD)
    val activeAdminTab: StateFlow<AdminTab> = _activeAdminTab.asStateFlow()

    private val _showAdminLoginDialog = MutableStateFlow(false)
    val showAdminLoginDialog: StateFlow<Boolean> = _showAdminLoginDialog.asStateFlow()

    private val _editingAccount = MutableStateFlow<AccountEntity?>(null)
    val editingAccount: StateFlow<AccountEntity?> = _editingAccount.asStateFlow()

    private val _showAccountEditDialog = MutableStateFlow(false)
    val showAccountEditDialog: StateFlow<Boolean> = _showAccountEditDialog.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
            // Quick 1.0s splash timer
            delay(1000)
            _isSplashShowing.value = false
        }
    }

    fun dismissSplash() {
        _isSplashShowing.value = false
    }

    fun openAdmin() {
        if (_isAdminLoggedIn.value) {
            _activeAdminTab.value = AdminTab.DASHBOARD
        } else {
            _showAdminLoginDialog.value = true
        }
    }

    fun closeAdminLoginDialog() {
        _showAdminLoginDialog.value = false
    }

    suspend fun tryAdminLogin(password: String): Boolean {
        val isValid = repository.verifyAdminPassword(password)
        if (isValid) {
            _isAdminLoggedIn.value = true
            _showAdminLoginDialog.value = false
            _activeAdminTab.value = AdminTab.DASHBOARD
            _statusMessage.value = "Admin Authority Unlocked"
            return true
        }
        return false
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _activeAdminTab.value = AdminTab.DASHBOARD
        _statusMessage.value = "Exited Admin Mode"
    }

    fun navigateAdminTab(tab: AdminTab) {
        _activeAdminTab.value = tab
    }

    fun updateProfile(
        creatorName: String,
        brandName: String,
        taglineBadges: String,
        email: String,
        contactTitle: String,
        contactSubtitle: String
    ) {
        viewModelScope.launch {
            val current = config.value
            val updated = current.copy(
                creatorName = creatorName.trim(),
                brandName = brandName.trim(),
                taglineBadges = taglineBadges.trim(),
                email = email.trim(),
                contactTitle = contactTitle.trim(),
                contactSubtitle = contactSubtitle.trim()
            )
            repository.updateConfig(updated)
            _statusMessage.value = "Profile information updated"
        }
    }

    fun updateAbout(aboutText: String) {
        viewModelScope.launch {
            val current = config.value
            val updated = current.copy(aboutBio = aboutText.trim())
            repository.updateConfig(updated)
            _statusMessage.value = "About text saved"
        }
    }

    fun updateAppearance(themeMode: String, accentHex: String) {
        viewModelScope.launch {
            val current = config.value
            val updated = current.copy(themeMode = themeMode, accentHex = accentHex)
            repository.updateConfig(updated)
            _statusMessage.value = "Appearance updated"
        }
    }

    fun updateThemeMode(themeMode: String) {
        viewModelScope.launch {
            val current = config.value
            val updated = current.copy(themeMode = themeMode)
            repository.updateConfig(updated)
        }
    }

    fun updateAppSettings(
        appName: String,
        splashEnabled: Boolean,
        showAbout: Boolean,
        showAccounts: Boolean,
        showContact: Boolean
    ) {
        viewModelScope.launch {
            val current = config.value
            val updated = current.copy(
                appName = appName.trim(),
                splashEnabled = splashEnabled,
                showAbout = showAbout,
                showAccounts = showAccounts,
                showContact = showContact
            )
            repository.updateConfig(updated)
            _statusMessage.value = "App Settings saved"
        }
    }

    fun updateAvatar(avatarUri: String?) {
        viewModelScope.launch {
            val current = config.value
            val updated = current.copy(customAvatarUri = avatarUri)
            repository.updateConfig(updated)
            _statusMessage.value = if (avatarUri != null) "Profile image updated" else "Reset to MA Logo"
        }
    }

    suspend fun changePassword(oldPass: String, newPass: String): Pair<Boolean, String> {
        val isOldValid = repository.verifyAdminPassword(oldPass)
        if (!isOldValid) {
            return Pair(false, "Current password is incorrect")
        }
        if (newPass.length < 4) {
            return Pair(false, "Password must be at least 4 characters")
        }
        repository.updatePassword(newPass)
        _statusMessage.value = "Admin password successfully changed"
        return Pair(true, "Password changed successfully")
    }

    fun startCreateAccount() {
        _editingAccount.value = null
        _showAccountEditDialog.value = true
    }

    fun startEditAccount(account: AccountEntity) {
        _editingAccount.value = account
        _showAccountEditDialog.value = true
    }

    fun dismissAccountEditDialog() {
        _showAccountEditDialog.value = false
        _editingAccount.value = null
    }

    fun saveAccount(account: AccountEntity) {
        viewModelScope.launch {
            repository.saveAccount(account)
            _showAccountEditDialog.value = false
            _editingAccount.value = null
            _statusMessage.value = "Account saved"
        }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            repository.deleteAccount(account)
            _statusMessage.value = "Account removed"
        }
    }

    fun toggleAccountVisibility(account: AccountEntity) {
        viewModelScope.launch {
            repository.toggleAccountVisibility(account)
        }
    }

    fun moveAccount(account: AccountEntity, direction: Int) {
        viewModelScope.launch {
            repository.moveAccount(account, direction, allAccounts.value)
        }
    }

    fun sortAccountsByName(ascending: Boolean) {
        viewModelScope.launch {
            repository.sortAccountsByName(ascending)
            _statusMessage.value = if (ascending) "Sorted A–Z" else "Sorted Z–A"
        }
    }

    suspend fun exportBackup(): String {
        return repository.exportBackupJson()
    }

    suspend fun importBackup(jsonString: String): Boolean {
        val success = repository.importBackupJson(jsonString)
        if (success) {
            _statusMessage.value = "Backup restored successfully"
        } else {
            _statusMessage.value = "Invalid backup data"
        }
        return success
    }

    fun resetToDefaults() {
        viewModelScope.launch {
            repository.resetToDefaults()
            _statusMessage.value = "Restored original default accounts & settings"
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }
}
