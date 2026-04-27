package com.leveluplife.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leveluplife.ui.components.rankBadge
import com.leveluplife.ui.theme.AccentBlue
import com.leveluplife.ui.theme.CardSurfaceAlt
import com.leveluplife.viewmodel.LevelUpViewModel

@Composable
fun HomeScreen(viewModel: LevelUpViewModel, paddingValues: PaddingValues) {
    val uiState by viewModel.uiState.collectAsState()
    val xpNeed = viewModel.xpNeededForNextLevel(uiState.profile.level).toFloat()
    val progressRaw = (uiState.profile.xp % xpNeed) / xpNeed
    val progress by animateFloatAsState(targetValue = progressRaw, animationSpec = tween(700), label = "xp_progress")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Level Up Life", style = MaterialTheme.typography.headlineLarge)
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "${rankBadge(uiState.profile.rank.name)} Rango ${uiState.profile.rank.name}: ${uiState.profile.rank.label}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("Nivel ${uiState.profile.level} · XP total ${uiState.profile.xp}")
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardSurfaceAlt),
                    color = AccentBlue
                )
                Text("Racha activa: ${uiState.profile.streak} días")
            }
        }

        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Misiones del día", style = MaterialTheme.typography.titleLarge)
                uiState.dailyMissions.take(3).forEach {
                    Text("• ${it.title}  (+${it.xpReward} XP)")
                }
            }
        }
    }
}
