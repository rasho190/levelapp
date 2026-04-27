package com.leveluplife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leveluplife.ui.components.MissionCard
import com.leveluplife.viewmodel.LevelUpViewModel

@Composable
fun MissionDetailScreen(
    viewModel: LevelUpViewModel,
    paddingValues: PaddingValues,
    onBack: () -> Unit
) {
    val mission by viewModel.currentMission.collectAsState()
    var evidence by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (mission == null) {
            Text("No hay misión seleccionada")
            Button(onClick = onBack) { Text("Volver") }
            return
        }

        MissionCard(mission = mission!!)

        OutlinedTextField(
            value = evidence,
            onValueChange = { evidence = it },
            label = { Text("Evidencia opcional") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            viewModel.completeMission(mission!!)
            onBack()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Completar misión")
        }
        Button(onClick = {
            viewModel.skipMission(mission!!)
            onBack()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Omitir")
        }
    }
}
