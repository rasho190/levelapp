package com.leveluplife.ui.components

import androidx.compose.ui.graphics.Color
import com.leveluplife.data.model.Difficulty
import com.leveluplife.data.model.MissionCategory
import com.leveluplife.ui.theme.AccentBlue
import com.leveluplife.ui.theme.AccentGold
import com.leveluplife.ui.theme.AccentGreen
import com.leveluplife.ui.theme.AccentPurple

fun categoryIcon(category: MissionCategory): String = when (category) {
    MissionCategory.HYGIENE -> "🧼"
    MissionCategory.NUTRITION -> "🥗"
    MissionCategory.EXPENSE -> "💰"
    MissionCategory.READING -> "📚"
    MissionCategory.LEARNING -> "🧠"
    MissionCategory.EXERCISE -> "🏋️"
    MissionCategory.WORK -> "💼"
    MissionCategory.ACADEMIC -> "🎓"
}

fun difficultyColor(difficulty: Difficulty): Color = when (difficulty) {
    Difficulty.EASY -> AccentGreen
    Difficulty.MEDIUM -> AccentBlue
    Difficulty.HARD -> AccentPurple
    Difficulty.EPIC -> AccentGold
}

fun rankBadge(rank: String): String = when (rank) {
    "E" -> "⚙️"
    "D" -> "🛡️"
    "C" -> "⚔️"
    "B" -> "🔥"
    "A" -> "💎"
    "S" -> "👑"
    "SS" -> "🏆"
    else -> "⭐"
}

fun achievementMedal(unlocked: Boolean): String = if (unlocked) "🏅" else "🔒"
