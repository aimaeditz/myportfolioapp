package com.example.data.local

import com.example.data.model.AccountEntity
import com.example.data.model.PlatformType
import com.example.data.model.PortfolioConfigEntity
import com.example.util.SecurityUtils

object DefaultData {
    fun getDefaultConfig(): PortfolioConfigEntity {
        val salt = SecurityUtils.generateSalt()
        val hash = SecurityUtils.hashPassword(SecurityUtils.DEFAULT_PASSWORD, salt)
        return PortfolioConfigEntity(
            id = 1,
            appName = "My Portfolio",
            creatorName = "M Abid",
            brandName = "AiMAEditz",
            taglineBadges = "AI Creator, Prompt Engineer, Digital Creator",
            aboutTitle = "About M Abid",
            aboutBio = "M Abid is an independent digital creator and the creator behind AiMAEditz, focused on AI, prompt engineering, digital creativity, visual content, editing and creative technology.",
            email = "aipromptxpert@gmail.com",
            contactTitle = "Let's Connect",
            contactSubtitle = "Open for creative collaborations, prompt engineering projects, or just to say hello.",
            customAvatarUri = null,
            themeMode = "LIGHT",
            accentHex = "#E9C176",
            splashEnabled = true,
            showAbout = true,
            showAccounts = true,
            showContact = true,
            passwordHash = hash,
            passwordSalt = salt
        )
    }

    fun getDefaultAccounts(): List<AccountEntity> {
        var order = 0
        return listOf(
            // YouTube
            AccountEntity(
                id = 1,
                platformType = PlatformType.YOUTUBE,
                title = "AiMAEditz",
                handle = "@aimabideditz",
                url = "https://www.youtube.com/@aimabideditz",
                orderIndex = order++,
                isVisible = true,
                category = "YouTube"
            ),
            AccountEntity(
                id = 2,
                platformType = PlatformType.YOUTUBE,
                title = "ShortMA29",
                handle = "@shortma29",
                url = "https://www.youtube.com/@shortma29",
                orderIndex = order++,
                isVisible = true,
                category = "YouTube"
            ),
            AccountEntity(
                id = 3,
                platformType = PlatformType.YOUTUBE,
                title = "AiMA Story Edit",
                handle = "@aimastoryedit",
                url = "https://www.youtube.com/@aimastoryedit",
                orderIndex = order++,
                isVisible = true,
                category = "YouTube"
            ),

            // Social Media - Instagram
            AccountEntity(
                id = 4,
                platformType = PlatformType.INSTAGRAM,
                title = "Instagram",
                handle = "@its_abid29",
                url = "https://www.instagram.com/its_abid29/",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),

            // Social Media - TikTok
            AccountEntity(
                id = 5,
                platformType = PlatformType.TIKTOK,
                title = "TikTok",
                handle = "@mabideditz",
                url = "https://www.tiktok.com/@mabideditz",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),
            AccountEntity(
                id = 6,
                platformType = PlatformType.TIKTOK,
                title = "TikTok",
                handle = "@its_abid29",
                url = "https://www.tiktok.com/@its_abid29",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),
            AccountEntity(
                id = 7,
                platformType = PlatformType.TIKTOK,
                title = "TikTok (Motivifyx)",
                handle = "@motivifyx_",
                url = "https://www.tiktok.com/@motivifyx_?lang=en",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),

            // Social Media - Facebook
            AccountEntity(
                id = 8,
                platformType = PlatformType.FACEBOOK,
                title = "M Abid",
                handle = "fb.mabid",
                url = "https://www.facebook.com/fb.mabid/",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),
            AccountEntity(
                id = 9,
                platformType = PlatformType.FACEBOOK,
                title = "AiMAEditz",
                handle = "aimaeditz",
                url = "https://www.facebook.com/aimaeditz",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),

            // Social Media - WhatsApp Channels
            AccountEntity(
                id = 10,
                platformType = PlatformType.WHATSAPP,
                title = "WhatsApp Channel 1",
                handle = "Updates & Edits",
                url = "https://www.whatsapp.com/channel/0029Vb669jh11ulG8ttZ3K3s",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),
            AccountEntity(
                id = 11,
                platformType = PlatformType.WHATSAPP,
                title = "WhatsApp Channel 2",
                handle = "AiMA Community",
                url = "https://whatsapp.com/channel/0029Vb6p9rh0VycFC0DPn10f",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),

            // Social Media - Threads
            AccountEntity(
                id = 12,
                platformType = PlatformType.THREADS,
                title = "Threads",
                handle = "@its_abid29",
                url = "https://www.threads.com/@its_abid29",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),

            // Social Media - Pinterest
            AccountEntity(
                id = 13,
                platformType = PlatformType.PINTEREST,
                title = "Pinterest",
                handle = "M Abid Rizvi",
                url = "https://www.pinterest.com/mabidrizvi/",
                orderIndex = order++,
                isVisible = true,
                category = "Social Media"
            ),

            // Professional - GitHub
            AccountEntity(
                id = 14,
                platformType = PlatformType.GITHUB,
                title = "GitHub",
                handle = "AiMAEditz",
                url = "https://github.com/aimaeditz",
                orderIndex = order++,
                isVisible = true,
                category = "Professional"
            ),

            // Professional - Fiverr
            AccountEntity(
                id = 15,
                platformType = PlatformType.FIVERR,
                title = "Fiverr",
                handle = "@its_abid29",
                url = "https://www.fiverr.com/its_abid29",
                orderIndex = order++,
                isVisible = true,
                category = "Professional"
            ),

            // Professional - SuperProfile
            AccountEntity(
                id = 16,
                platformType = PlatformType.SUPERPROFILE,
                title = "SuperProfile",
                handle = "AiMAEditz",
                url = "https://superprofile.bio/aimaeditz",
                orderIndex = order++,
                isVisible = true,
                category = "Professional"
            ),

            // Websites - Link Hub
            AccountEntity(
                id = 17,
                platformType = PlatformType.LINK_HUB,
                title = "Link Hub",
                handle = "link.me/maeditz",
                url = "https://link.me/maeditz",
                orderIndex = order++,
                isVisible = true,
                category = "Websites"
            ),

            // Blogger - Websites & Blogs
            AccountEntity(
                id = 18,
                platformType = PlatformType.BLOGGER,
                title = "AI Prompt Xpert",
                handle = "aipromptxpert.blogspot.com",
                url = "https://aipromptxpert.blogspot.com/",
                orderIndex = order++,
                isVisible = true,
                category = "Blogger"
            ),
            AccountEntity(
                id = 19,
                platformType = PlatformType.BLOGGER,
                title = "AiMAEditz Blog",
                handle = "aimaeditz.blogspot.com",
                url = "https://aimaeditz.blogspot.com/",
                orderIndex = order++,
                isVisible = true,
                category = "Blogger"
            )
        )
    }
}
