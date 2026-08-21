package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio_config")
data class PortfolioConfigEntity(
    @PrimaryKey
    val id: Int = 1,
    val appName: String = "My Portfolio",
    val creatorName: String = "M Abid",
    val brandName: String = "AiMAEditz",
    val taglineBadges: String = "AI Creator, Prompt Engineer, Digital Creator",
    val aboutTitle: String = "About M Abid",
    val aboutBio: String = "M Abid is an independent digital creator and the creator behind AiMAEditz, focused on AI, prompt engineering, digital creativity, visual content, editing and creative technology.",
    val email: String = "aipromptxpert@gmail.com",
    val contactTitle: String = "Let's Connect",
    val contactSubtitle: String = "Open for creative collaborations, prompt engineering projects, or just to say hello.",
    val customAvatarUri: String? = null,
    val themeMode: String = "LIGHT", // "LIGHT", "DARK", "SYSTEM"
    val accentHex: String = "#E9C176", // Muted Gold default
    val splashEnabled: Boolean = true,
    val showAbout: Boolean = true,
    val showAccounts: Boolean = true,
    val showContact: Boolean = true,
    val passwordHash: String = "",
    val passwordSalt: String = ""
)
