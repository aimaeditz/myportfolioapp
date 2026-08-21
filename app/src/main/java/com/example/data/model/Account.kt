package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

object AccountCategory {
    const val SOCIAL_MEDIA = "Social Media"
    const val YOUTUBE = "YouTube"
    const val BLOGGER = "Blogger"
    const val WEBSITES = "Websites"
    const val PROFESSIONAL = "Professional"

    val ALL = listOf(
        SOCIAL_MEDIA,
        YOUTUBE,
        BLOGGER,
        WEBSITES,
        PROFESSIONAL
    )

    fun normalize(category: String?, platformType: PlatformType? = null): String {
        val cat = category?.trim().orEmpty()
        return when {
            cat.equals(SOCIAL_MEDIA, ignoreCase = true) -> SOCIAL_MEDIA
            cat.equals(YOUTUBE, ignoreCase = true) -> YOUTUBE
            cat.equals(BLOGGER, ignoreCase = true) || cat.equals("Blog", ignoreCase = true) || cat.contains("blogger", ignoreCase = true) -> BLOGGER
            cat.equals(WEBSITES, ignoreCase = true) || cat.equals("Website", ignoreCase = true) || cat.equals("Web", ignoreCase = true) -> WEBSITES
            cat.equals(PROFESSIONAL, ignoreCase = true) -> PROFESSIONAL

            // Legacy category names mapping
            cat.equals("Social", ignoreCase = true) || cat.equals("Messaging", ignoreCase = true) ||
            cat.equals("Creative", ignoreCase = true) -> SOCIAL_MEDIA
            cat.equals("Video", ignoreCase = true) -> if (platformType == PlatformType.YOUTUBE) YOUTUBE else SOCIAL_MEDIA
            cat.equals("Development", ignoreCase = true) || cat.equals("Freelance", ignoreCase = true) || cat.equals("Profile", ignoreCase = true) -> PROFESSIONAL
            cat.equals("Links", ignoreCase = true) || cat.equals("Link Hub", ignoreCase = true) -> WEBSITES

            // Fallback by platform type
            platformType != null -> when (platformType) {
                PlatformType.YOUTUBE -> YOUTUBE
                PlatformType.INSTAGRAM, PlatformType.TIKTOK, PlatformType.FACEBOOK,
                PlatformType.WHATSAPP, PlatformType.THREADS, PlatformType.PINTEREST -> SOCIAL_MEDIA
                PlatformType.BLOGGER -> BLOGGER
                PlatformType.GITHUB, PlatformType.FIVERR, PlatformType.SUPERPROFILE -> PROFESSIONAL
                PlatformType.WEBSITE, PlatformType.LINK_HUB, PlatformType.OTHER -> WEBSITES
            }
            else -> SOCIAL_MEDIA
        }
    }
}

enum class PlatformType(
    val displayName: String,
    val defaultIconName: String,
    val defaultCategory: String
) {
    YOUTUBE("YouTube", "play_circle", AccountCategory.YOUTUBE),
    INSTAGRAM("Instagram", "photo_camera", AccountCategory.SOCIAL_MEDIA),
    TIKTOK("TikTok", "music_note", AccountCategory.SOCIAL_MEDIA),
    FACEBOOK("Facebook", "thumb_up", AccountCategory.SOCIAL_MEDIA),
    WHATSAPP("WhatsApp", "chat", AccountCategory.SOCIAL_MEDIA),
    THREADS("Threads", "alternate_email", AccountCategory.SOCIAL_MEDIA),
    PINTEREST("Pinterest", "push_pin", AccountCategory.SOCIAL_MEDIA),
    GITHUB("GitHub", "code", AccountCategory.PROFESSIONAL),
    FIVERR("Fiverr", "work", AccountCategory.PROFESSIONAL),
    SUPERPROFILE("SuperProfile", "badge", AccountCategory.PROFESSIONAL),
    BLOGGER("Blogger", "article", AccountCategory.BLOGGER),
    WEBSITE("Website", "language", AccountCategory.WEBSITES),
    LINK_HUB("Link Hub", "link", AccountCategory.WEBSITES),
    OTHER("Other", "open_in_new", AccountCategory.WEBSITES)
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
    val category: String = platformType.defaultCategory
)
