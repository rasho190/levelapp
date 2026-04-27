package com.leveluplife.data.repository

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leveluplife.data.database.LevelUpDatabase
import com.leveluplife.data.model.Achievement
import com.leveluplife.data.model.CategoryProgress
import com.leveluplife.data.model.ExpenseLog
import com.leveluplife.data.model.Frequency
import com.leveluplife.data.model.Mission
import com.leveluplife.data.model.MissionCategory
import com.leveluplife.data.model.UserProfile
import com.leveluplife.data.model.UserRank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LevelUpRepository(
    private val db: LevelUpDatabase,
    private val assets: AssetManager
) {
    private val dao = db.dao()

    fun observeProfile(): Flow<UserProfile?> = dao.observeUserProfile()
    fun observeAllMissions(): Flow<List<Mission>> = dao.observeAllMissions()
    fun observeMissionsByFrequency(frequency: Frequency): Flow<List<Mission>> = dao.observeMissionsByFrequency(frequency)
    fun observeCategoryProgress(): Flow<List<CategoryProgress>> = dao.observeCategoryProgress()
    fun observeAchievements(): Flow<List<Achievement>> = dao.observeAchievements()
    fun observeExpenseLogs(): Flow<List<ExpenseLog>> = dao.observeExpenseLogs()

    suspend fun bootstrapIfNeeded() {
        if (dao.missionCount() > 0) return

        dao.upsertUserProfile(UserProfile())
        dao.insertMissions(loadSeedMissions())
        dao.upsertCategoryProgress(
            MissionCategory.entries.map { category ->
                CategoryProgress(categoryName = category)
            }
        )
        dao.upsertAchievement(defaultAchievements())
    }

    suspend fun completeMission(mission: Mission) {
        if (mission.isCompleted) return

        val completedMission = mission.copy(isCompleted = true, completedAt = System.currentTimeMillis())
        dao.updateMission(completedMission)

        val current = dao.observeUserProfile().first() ?: UserProfile()
        val updatedXp = current.xp + mission.xpReward
        val updatedLevel = calculateLevel(updatedXp)
        val updated = current.copy(
            xp = updatedXp,
            level = updatedLevel,
            rank = rankForLevel(updatedLevel),
            streak = current.streak + 1
        )
        dao.upsertUserProfile(updated)
    }

    suspend fun skipMission(mission: Mission) {
        val current = dao.observeUserProfile().first() ?: UserProfile()
        val newStreak = (current.streak - 1).coerceAtLeast(0)
        dao.upsertUserProfile(current.copy(streak = newStreak))
        dao.updateMission(mission.copy(isCompleted = false, completedAt = null))
    }

    suspend fun addExpense(amount: Double, description: String, category: String = "General") {
        dao.insertExpense(
            ExpenseLog(
                date = java.time.LocalDate.now().toString(),
                amount = amount,
                description = description,
                category = category
            )
        )
    }

    suspend fun updateProfileSettings(
        budget: Double,
        language: String,
        academicGoal: String,
        workGoal: String
    ) {
        val current = dao.observeUserProfile().first() ?: UserProfile()
        dao.upsertUserProfile(
            current.copy(
                dailyBudget = budget,
                selectedLanguage = language,
                academicGoal = academicGoal,
                workGoal = workGoal
            )
        )
    }

    private fun calculateLevel(totalXp: Int): Int {
        var level = 1
        var remaining = totalXp
        while (remaining >= level * 100) {
            remaining -= level * 100
            level++
        }
        return level
    }

    fun xpToNextLevel(level: Int): Int = level * 100

    private fun rankForLevel(level: Int): UserRank {
        return UserRank.entries.last { level >= it.minLevel }
    }

    private fun loadSeedMissions(): List<Mission> {
        val json = assets.open("missions_seed.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<MissionSeedDto>>() {}.type
        val parsed: List<MissionSeedDto> = Gson().fromJson(json, listType)
        return parsed.map {
            Mission(
                title = it.title,
                description = it.description,
                category = MissionCategory.valueOf(it.category),
                difficulty = com.leveluplife.data.model.Difficulty.valueOf(it.difficulty),
                frequency = Frequency.valueOf(it.frequency),
                xpReward = it.xpReward
            )
        }
    }

    private fun defaultAchievements(): List<Achievement> = listOf(
        Achievement(title = "Primer Paso", description = "Completa tu primera misión", icon = "🥉", condition = "1 misión"),
        Achievement(title = "Racha de Fuego", description = "Mantén 7 días seguidos", icon = "🔥", condition = "racha 7"),
        Achievement(title = "Monje del Enfoque", description = "100 misiones completadas", icon = "🏆", condition = "100 misiones")
    )
}

data class MissionSeedDto(
    val title: String,
    val description: String,
    val category: String,
    val difficulty: String,
    val frequency: String,
    val xpReward: Int
)
