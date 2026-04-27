package com.leveluplife.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.leveluplife.ui.screens.AchievementsScreen
import com.leveluplife.ui.screens.CategoriesScreen
import com.leveluplife.ui.screens.HomeScreen
import com.leveluplife.ui.screens.MissionDetailScreen
import com.leveluplife.ui.screens.MissionsScreen
import com.leveluplife.ui.screens.SettingsScreen
import com.leveluplife.ui.screens.StatsScreen
import com.leveluplife.viewmodel.LevelUpViewModel

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Inicio")
    data object Categories : Screen("categories", "Categorías")
    data object Missions : Screen("missions", "Misiones")
    data object Stats : Screen("stats", "Estadísticas")
    data object Achievements : Screen("achievements", "Logros")
    data object Settings : Screen("settings", "Config")
    data object MissionDetail : Screen("mission_detail", "Detalle")
}

@Composable
fun LevelUpNavGraph(viewModel: LevelUpViewModel) {
    val navController = rememberNavController()
    val tabs = listOf(
        Screen.Home,
        Screen.Categories,
        Screen.Missions,
        Screen.Stats,
        Screen.Achievements,
        Screen.Settings
    )

    fun iconFor(screen: Screen) = when (screen) {
        Screen.Home -> Icons.Default.Home
        Screen.Categories -> Icons.Default.Star
        Screen.Missions -> Icons.Default.List
        Screen.Stats -> Icons.Default.Timeline
        Screen.Achievements -> Icons.Default.EmojiEvents
        Screen.Settings -> Icons.Default.Settings
        Screen.MissionDetail -> Icons.Default.List
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                tabs.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = { navController.navigate(screen.route) },
                        label = { Text(screen.title) },
                        icon = { Icon(imageVector = iconFor(screen), contentDescription = screen.title) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen(viewModel = viewModel, paddingValues = padding)
            }
            composable(Screen.Categories.route) {
                CategoriesScreen(viewModel = viewModel, paddingValues = padding)
            }
            composable(Screen.Missions.route) {
                MissionsScreen(
                    viewModel = viewModel,
                    paddingValues = padding,
                    onOpenMission = {
                        viewModel.pickMission(it)
                        navController.navigate(Screen.MissionDetail.route)
                    }
                )
            }
            composable(Screen.Stats.route) {
                StatsScreen(viewModel = viewModel, paddingValues = padding)
            }
            composable(Screen.Achievements.route) {
                AchievementsScreen(viewModel = viewModel, paddingValues = padding)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = viewModel, paddingValues = padding)
            }
            composable(Screen.MissionDetail.route) {
                MissionDetailScreen(
                    viewModel = viewModel,
                    paddingValues = padding,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
