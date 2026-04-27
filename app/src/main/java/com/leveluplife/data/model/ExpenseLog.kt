package com.leveluplife.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_log")
data class ExpenseLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val amount: Double,
    val description: String,
    val category: String = "General"
)
