package com.leveluplife.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_progress")
data class CategoryProgress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoryName: MissionCategory,
    val level: Int = 1,
    val xp: Int = 0,
    val completedMissions: Int = 0,
    val totalMissions: Int = 500
)
