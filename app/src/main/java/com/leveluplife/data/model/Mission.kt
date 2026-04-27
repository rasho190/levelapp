package com.leveluplife.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mission")
data class Mission(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: MissionCategory,
    val difficulty: Difficulty,
    val frequency: Frequency,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)
