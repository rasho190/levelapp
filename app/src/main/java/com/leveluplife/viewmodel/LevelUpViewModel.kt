package com.leveluplife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leveluplife.data.model.Achievement
import com.leveluplife.data.model.CategoryProgress
import com.leveluplife.data.model.ExpenseLog
import com.leveluplife.data.model.Frequency
import com.leveluplife.data.model.Mission
import com.leveluplife.data.model.UserProfile
import com.leveluplife.data.repository.LevelUpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LevelUpUiState(
    val profile: UserProfile = UserProfile(),
    val dailyMissions: List<Mission> = emptyList(),
    val weeklyMissions: List<Mission> = emptyList(),
    val monthlyMissions: List<Mission> = emptyList(),
    val categories: List<CategoryProgress> = emptyList(),
    val achievements: List<Achievement> = emptyList(),
    val expenses: List<ExpenseLog> = emptyList()
)

class LevelUpViewModel(private val repository: LevelUpRepository) : ViewModel() {
    private val selectedMission = MutableStateFlow<Mission?>(null)

    val uiState: StateFlow<LevelUpUiState> = combine(
        repository.observeProfile(),
        repository.observeMissionsByFrequency(Frequency.DAILY),
        repository.observeMissionsByFrequency(Frequency.WEEKLY),
        repository.observeMissionsByFrequency(Frequency.MONTHLY),
        repository.observeCategoryProgress(),
        repository.observeAchievements(),
        repository.observeExpenseLogs()
    ) { profile, daily, weekly, monthly, categories, achievements, expenses ->
        LevelUpUiState(
            profile = profile ?: UserProfile(),
            dailyMissions = daily,
            weeklyMissions = weekly,
            monthlyMissions = monthly,
            categories = categories,
            achievements = achievements,
            expenses = expenses
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LevelUpUiState())

    val currentMission: StateFlow<Mission?> = selectedMission

    init {
        viewModelScope.launch { repository.bootstrapIfNeeded() }
    }

    fun pickMission(mission: Mission) {
        selectedMission.value = mission
    }

    fun completeMission(mission: Mission) {
        viewModelScope.launch { repository.completeMission(mission) }
    }

    fun skipMission(mission: Mission) {
        viewModelScope.launch { repository.skipMission(mission) }
    }

    fun saveSettings(budget: Double, language: String, academic: String, work: String) {
        viewModelScope.launch { repository.updateProfileSettings(budget, language, academic, work) }
    }

    fun addExpense(amount: Double, description: String) {
        viewModelScope.launch { repository.addExpense(amount, description) }
    }

    fun xpNeededForNextLevel(level: Int): Int = repository.xpToNextLevel(level)
}
