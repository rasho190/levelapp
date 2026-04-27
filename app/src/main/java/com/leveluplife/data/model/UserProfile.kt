package com.leveluplife.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "Heroe",
    val level: Int = 1,
    val xp: Int = 0,
    val rank: UserRank = UserRank.E,
    val streak: Int = 0,
    val dailyBudget: Double = 50.0,
    val selectedLanguage: String = "English",
    val academicGoal: String = "Completar sesiones de estudio diarias",
    val workGoal: String = "Fortalecer perfil profesional"
)
