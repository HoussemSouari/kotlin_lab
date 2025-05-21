package com.example.houssem_souari.ui.theme

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.houssem_souari.viewModel.CountryGameUiState
import com.example.houssem_souari.viewModel.CountryGameViewModel

@Composable
fun ScoreScreen(
    uiState: CountryGameUiState,
    viewModel: CountryGameViewModel,
    onRestartGame: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Game Over!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Your final score:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "${uiState.score} / ${uiState.questionsAsked}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                viewModel.resetGame()
                onRestartGame()
            }) {
                Text("Play Again")
            }

            Button(onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "I scored ${uiState.score} out of ${uiState.questionsAsked} in the Country Game!"
                    )
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share score via"))
            }) {
                Text("Share Score")
            }
        }
    }
}