package com.leveluplife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leveluplife.data.model.Mission
import com.leveluplife.ui.components.MissionCard
import com.leveluplife.viewmodel.LevelUpViewModel

@Composable
fun MissionsScreen(
    viewModel: LevelUpViewModel,
    paddingValues: PaddingValues,
    onOpenMission: (Mission) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Diarias", style = MaterialTheme.typography.titleLarge) }
        items(uiState.dailyMissions) { mission -> MissionCard(mission = mission) { onOpenMission(mission) } }

        item { Text("Semanales", style = MaterialTheme.typography.titleLarge) }
        items(uiState.weeklyMissions) { mission -> MissionCard(mission = mission) { onOpenMission(mission) } }

        item { Text("Mensuales", style = MaterialTheme.typography.titleLarge) }
        items(uiState.monthlyMissions) { mission -> MissionCard(mission = mission) { onOpenMission(mission) } }
    }
}
