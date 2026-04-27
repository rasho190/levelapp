package com.leveluplife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leveluplife.ui.components.achievementMedal
import com.leveluplife.viewmodel.LevelUpViewModel

@Composable
fun AchievementsScreen(viewModel: LevelUpViewModel, paddingValues: PaddingValues) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(uiState.achievements) {
            Card(shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("${achievementMedal(it.isUnlocked)} ${it.icon} ${it.title}")
                    Text(it.description)
                    Text("Condición: ${it.condition}", style = MaterialTheme.typography.bodyMedium)
                    Text(if (it.isUnlocked) "Desbloqueado" else "Bloqueado")
                }
            }
        }
    }
}
