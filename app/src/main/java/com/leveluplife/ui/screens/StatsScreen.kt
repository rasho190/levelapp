package com.leveluplife.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leveluplife.data.model.MissionCategory
import com.leveluplife.viewmodel.LevelUpViewModel

@Composable
fun StatsScreen(viewModel: LevelUpViewModel, paddingValues: PaddingValues) {
    val uiState by viewModel.uiState.collectAsState()
    val completed = (uiState.dailyMissions + uiState.weeklyMissions + uiState.monthlyMissions).count { it.isCompleted }
    val avgExpense = uiState.expenses.map { it.amount }.average().takeIf { !it.isNaN() } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text("XP ganado: ${uiState.profile.xp}")
        Text("Misiones completadas: $completed")
        Text("Categoría fuerte: ${uiState.categories.maxByOrNull { it.completedMissions }?.categoryName?.displayName ?: "-"}")
        Text("Categoría débil: ${uiState.categories.minByOrNull { it.completedMissions }?.categoryName?.displayName ?: "-"}")
        Text("Gasto promedio diario: S/ ${"%.2f".format(avgExpense)}")
        Text("Días de lectura: ${uiState.dailyMissions.count { it.category == MissionCategory.READING && it.isCompleted }}")
        Text("Días de ejercicio: ${uiState.dailyMissions.count { it.category == MissionCategory.EXERCISE && it.isCompleted }}")
        Text("Racha máxima (actual prototipo): ${uiState.profile.streak}")
    }
}
