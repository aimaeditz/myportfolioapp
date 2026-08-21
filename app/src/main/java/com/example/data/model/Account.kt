package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PlatformType(
    val displayName: String,
    val defaultIconName: String,
    val category: String
) {
    YOUTUBE("YouTube", "play_circle", "Video"),
    INSTAGRAM("Instagram", "photo_camera", "Social"),
    TIKTOK("TikTok", "music_note", "Video"),
    FACEBOOK("Facebook", "thumb_up", "Social"),
    WHATSAPP("WhatsApp", "chat", "Messaging"),
    THREADS("Threads", "alternate_email", "Social"),
    PINTEREST("Pinterest", "push_pin", "Creative"),
    GITHUB("GitHub", "code", "Development"),
    FIVERR("Fiverr", "work", "Freelance"),
    SUPERPROFILE("SuperProfile", "badge", "Profile"),
    LINK_HUB("Link Hub", "link", "Links"),
    WEBSITE("Website / Blog", "language", "Web"),
    OTHER("Other", "open_in_new", "Links")
}

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val platformType: PlatformType,
    val title: String,
    val handle: String,
    val url: String,
    val orderIndex: Int = 0,
    val isVisible: Boolean = true,
    val category: String = platformType.category
)
