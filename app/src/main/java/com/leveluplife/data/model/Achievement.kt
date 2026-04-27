package com.leveluplife.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievement")
data class Achievement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val icon: String,
    val condition: String,
    val isUnlocked: Boolean = false
)
