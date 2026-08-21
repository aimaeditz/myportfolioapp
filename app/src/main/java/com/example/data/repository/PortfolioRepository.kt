package com.example.data.repository

import com.example.data.local.AccountDao
import com.example.data.local.ConfigDao
import com.example.data.local.DefaultData
import com.example.data.model.AccountEntity
import com.example.data.model.PortfolioConfigEntity
import com.example.util.SecurityUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class PortfolioRepository(
    private val accountDao: AccountDao,
    private val configDao: ConfigDao
) {
    val allAccounts: Flow<List<AccountEntity>> = accountDao.getAllAccounts()

    val visibleAccounts: Flow<List<AccountEntity>> = accountDao.getVisibleAccounts()

    val config: Flow<PortfolioConfigEntity> = configDao.getConfigFlow().map { configEntity ->
        configEntity ?: DefaultData.getDefaultConfig()
    }

    suspend fun ensureInitialized() = withContext(Dispatchers.IO) {
        val existingConfig = configDao.getConfig()
        if (existingConfig == null) {
            configDao.insertConfig(DefaultData.getDefaultConfig())
        }
        val count = accountDao.getCount()
        if (count == 0) {
            accountDao.insertAccounts(DefaultData.getDefaultAccounts())
        } else {
            // Normalize categories if any existing accounts have legacy category names
            val currentAccounts = accountDao.getAllAccountsList()
            var hasUpdates = false
            val updatedList = currentAccounts.map { acc ->
                val norm = com.example.data.model.AccountCategory.normalize(acc.category, acc.platformType)
                if (acc.category != norm) {
                    hasUpdates = true
                    acc.copy(category = norm)
                } else {
                    acc
                }
            }
            if (hasUpdates) {
                accountDao.insertAccounts(updatedList)
            }
        }
    }

    suspend fun saveAccount(account: AccountEntity) = withContext(Dispatchers.IO) {
        if (account.id == 0L) {
            accountDao.insertAccount(account)
        } else {
            accountDao.updateAccount(account)
        }
    }

    suspend fun deleteAccount(account: AccountEntity) = withContext(Dispatchers.IO) {
        accountDao.deleteAccount(account)
    }

    suspend fun deleteAccountById(id: Long) = withContext(Dispatchers.IO) {
        accountDao.deleteAccountById(id)
    }

    suspend fun toggleAccountVisibility(account: AccountEntity) = withContext(Dispatchers.IO) {
        accountDao.updateAccount(account.copy(isVisible = !account.isVisible))
    }

    suspend fun reorderAccounts(accounts: List<AccountEntity>) = withContext(Dispatchers.IO) {
        accounts.forEachIndexed { index, account ->
            accountDao.updateAccount(account.copy(orderIndex = index))
        }
    }

    suspend fun moveAccount(account: AccountEntity, direction: Int, allCurrentList: List<AccountEntity>) = withContext(Dispatchers.IO) {
        val index = allCurrentList.indexOfFirst { it.id == account.id }
        val targetIndex = index + direction
        if (index >= 0 && targetIndex in allCurrentList.indices) {
            val mutable = allCurrentList.toMutableList()
            val item = mutable.removeAt(index)
            mutable.add(targetIndex, item)
            reorderAccounts(mutable)
        }
    }

    suspend fun sortAccountsByName(ascending: Boolean) = withContext(Dispatchers.IO) {
        val currentList = accountDao.getAllAccountsList()
        val sortedList = if (ascending) {
            currentList.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        } else {
            currentList.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) { it.title })
        }
        reorderAccounts(sortedList)
    }

    suspend fun updateConfig(configEntity: PortfolioConfigEntity) = withContext(Dispatchers.IO) {
        configDao.insertConfig(configEntity)
    }

    suspend fun updatePassword(newPlainPassword: String): Boolean = withContext(Dispatchers.IO) {
        val currentConfig = configDao.getConfig() ?: DefaultData.getDefaultConfig()
        val salt = SecurityUtils.generateSalt()
        val hash = SecurityUtils.hashPassword(newPlainPassword, salt)
        configDao.insertConfig(
            currentConfig.copy(
                passwordHash = hash,
                passwordSalt = salt
            )
        )
        true
    }

    suspend fun verifyAdminPassword(enteredPassword: String): Boolean = withContext(Dispatchers.IO) {
        val currentConfig = configDao.getConfig() ?: DefaultData.getDefaultConfig()
        SecurityUtils.verifyPassword(enteredPassword, currentConfig.passwordHash, currentConfig.passwordSalt)
    }

    suspend fun resetToDefaults() = withContext(Dispatchers.IO) {
        accountDao.deleteAllAccounts()
        accountDao.insertAccounts(DefaultData.getDefaultAccounts())
        configDao.insertConfig(DefaultData.getDefaultConfig())
    }

    suspend fun exportBackupJson(): String = withContext(Dispatchers.IO) {
        val currentConfig = configDao.getConfig() ?: DefaultData.getDefaultConfig()
        val root = JSONObject()
        val configObj = JSONObject().apply {
            put("appName", currentConfig.appName)
            put("creatorName", currentConfig.creatorName)
            put("brandName", currentConfig.brandName)
            put("taglineBadges", currentConfig.taglineBadges)
            put("aboutTitle", currentConfig.aboutTitle)
            put("aboutBio", currentConfig.aboutBio)
            put("email", currentConfig.email)
            put("contactTitle", currentConfig.contactTitle)
            put("contactSubtitle", currentConfig.contactSubtitle)
            put("customAvatarUri", currentConfig.customAvatarUri ?: "")
            put("themeMode", currentConfig.themeMode)
            put("accentHex", currentConfig.accentHex)
            put("splashEnabled", currentConfig.splashEnabled)
            put("showAbout", currentConfig.showAbout)
            put("showAccounts", currentConfig.showAccounts)
            put("showContact", currentConfig.showContact)
        }
        root.put("config", configObj)

        val accountsArray = JSONArray()
        val allAccs = accountDao.getAllAccountsList()
        allAccs.forEach { acc ->
            val accObj = JSONObject().apply {
                put("platformType", acc.platformType.name)
                put("title", acc.title)
                put("handle", acc.handle)
                put("url", acc.url)
                put("orderIndex", acc.orderIndex)
                put("isVisible", acc.isVisible)
                put("category", acc.category)
            }
            accountsArray.put(accObj)
        }
        root.put("accounts", accountsArray)
        root.put("exportedAt", System.currentTimeMillis())
        root.put("version", 1)
        root.toString(2)
    }

    suspend fun importBackupJson(jsonString: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val root = JSONObject(jsonString)
            if (root.has("config")) {
                val configObj = root.getJSONObject("config")
                val currentConfig = configDao.getConfig() ?: DefaultData.getDefaultConfig()
                val newConfig = currentConfig.copy(
                    appName = configObj.optString("appName", currentConfig.appName),
                    creatorName = configObj.optString("creatorName", currentConfig.creatorName),
                    brandName = configObj.optString("brandName", currentConfig.brandName),
                    taglineBadges = configObj.optString("taglineBadges", currentConfig.taglineBadges),
                    aboutTitle = configObj.optString("aboutTitle", currentConfig.aboutTitle),
                    aboutBio = configObj.optString("aboutBio", currentConfig.aboutBio),
                    email = configObj.optString("email", currentConfig.email),
                    contactTitle = configObj.optString("contactTitle", currentConfig.contactTitle),
                    contactSubtitle = configObj.optString("contactSubtitle", currentConfig.contactSubtitle),
                    customAvatarUri = configObj.optString("customAvatarUri").takeIf { it.isNotBlank() },
                    themeMode = configObj.optString("themeMode", currentConfig.themeMode),
                    accentHex = configObj.optString("accentHex", currentConfig.accentHex),
                    splashEnabled = configObj.optBoolean("splashEnabled", currentConfig.splashEnabled),
                    showAbout = configObj.optBoolean("showAbout", currentConfig.showAbout),
                    showAccounts = configObj.optBoolean("showAccounts", currentConfig.showAccounts),
                    showContact = configObj.optBoolean("showContact", currentConfig.showContact)
                )
                configDao.insertConfig(newConfig)
            }

            if (root.has("accounts")) {
                val accountsArray = root.getJSONArray("accounts")
                val importedList = mutableListOf<AccountEntity>()
                for (i in 0 until accountsArray.length()) {
                    val accObj = accountsArray.getJSONObject(i)
                    val platformStr = accObj.optString("platformType", "OTHER")
                    val platform = try {
                        com.example.data.model.PlatformType.valueOf(platformStr)
                    } catch (e: Exception) {
                        com.example.data.model.PlatformType.OTHER
                    }
                    importedList.add(
                        AccountEntity(
                            id = 0, // Auto-generate / re-insert
                            platformType = platform,
                            title = accObj.optString("title", ""),
                            handle = accObj.optString("handle", ""),
                            url = accObj.optString("url", ""),
                            orderIndex = accObj.optInt("orderIndex", i),
                            isVisible = accObj.optBoolean("isVisible", true),
                            category = com.example.data.model.AccountCategory.normalize(accObj.optString("category", ""), platform)
                        )
                    )
                }
                if (importedList.isNotEmpty()) {
                    accountDao.deleteAllAccounts()
                    accountDao.insertAccounts(importedList)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
