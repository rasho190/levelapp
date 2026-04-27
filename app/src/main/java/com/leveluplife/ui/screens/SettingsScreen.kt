package com.leveluplife.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leveluplife.viewmodel.LevelUpViewModel

@Composable
fun SettingsScreen(viewModel: LevelUpViewModel, paddingValues: PaddingValues) {
    val uiState by viewModel.uiState.collectAsState()

    var budget by remember(uiState.profile.dailyBudget) { mutableStateOf(uiState.profile.dailyBudget.toString()) }
    var language by remember(uiState.profile.selectedLanguage) { mutableStateOf(uiState.profile.selectedLanguage) }
    var academic by remember(uiState.profile.academicGoal) { mutableStateOf(uiState.profile.academicGoal) }
    var work by remember(uiState.profile.workGoal) { mutableStateOf(uiState.profile.workGoal) }
    var expenseAmount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(value = budget, onValueChange = { budget = it }, label = { Text("Presupuesto diario máximo (S/)") })
        OutlinedTextField(value = language, onValueChange = { language = it }, label = { Text("Idioma objetivo") })
        OutlinedTextField(value = academic, onValueChange = { academic = it }, label = { Text("Meta académica") })
        OutlinedTextField(value = work, onValueChange = { work = it }, label = { Text("Meta laboral") })

        Button(onClick = {
            viewModel.saveSettings(
                budget = budget.toDoubleOrNull() ?: 50.0,
                language = language,
                academic = academic,
                work = work
            )
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar configuración")
        }

        OutlinedTextField(value = expenseAmount, onValueChange = { expenseAmount = it }, label = { Text("Registrar gasto del día") })
        Button(onClick = {
            val amount = expenseAmount.toDoubleOrNull() ?: return@Button
            viewModel.addExpense(amount, "Gasto manual")
            expenseAmount = ""
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrar gasto")
        }

        Text("Exportar progreso: disponible para próxima iteración (Room + JSON/CSV).")
    }
}
