package com.leveluplife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.leveluplife.data.database.LevelUpDatabase
import com.leveluplife.data.repository.LevelUpRepository
import com.leveluplife.navigation.LevelUpNavGraph
import com.leveluplife.ui.theme.LevelUpLifeTheme
import com.leveluplife.viewmodel.LevelUpViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = LevelUpDatabase.getInstance(applicationContext)
        val repository = LevelUpRepository(db, applicationContext.assets)
        val viewModel = LevelUpViewModel(repository)

        setContent {
            LevelUpLifeTheme {
                LevelUpNavGraph(viewModel = viewModel)
            }
        }
    }
}
