package com.example.houssem_souari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.houssem_souari.ui.theme.Houssem_SouariTheme
import com.example.houssem_souari.ui.theme.QuestionScreen
import com.example.houssem_souari.ui.theme.ResultScreen
import com.example.houssem_souari.ui.theme.ScoreScreen
import com.example.houssem_souari.ui.theme.Houssem_SouariTheme
import com.example.houssem_souari.viewModel.CountryGameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Houssem_SouariTheme {
                CountryGameApp()
            }
        }
    }
}

@Composable
fun CountryGameApp() {
    val navController = rememberNavController()
    val viewModel: CountryGameViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "question") {
        composable("question") {
            QuestionScreen(
                uiState = uiState,
                viewModel = viewModel,
                onNavigateToResult = { navController.navigate("result") }
            )
        }
        composable("result") {
            ResultScreen(
                uiState = uiState,
                viewModel = viewModel,
                onNavigateToQuestion = {
                    navController.popBackStack()
                    navController.navigate("question")
                },
                onNavigateToScore = { navController.navigate("score") }
            )
        }
        composable("score") {
            ScoreScreen(
                uiState = uiState,
                viewModel = viewModel,
                onRestartGame = {
                    navController.popBackStack(navController.graph.startDestinationId, false)
                }
            )
        }
    }

    if (uiState.isGameOver && navController.currentDestination?.route != "score") {
        navController.navigate("score") {
            popUpTo("question") { inclusive = true }
        }
    }
}
