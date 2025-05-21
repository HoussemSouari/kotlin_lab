package com.example.houssem_souari.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.houssem_souari.viewModel.CountryGameUiState
import com.example.houssem_souari.viewModel.CountryGameViewModel

@Composable
fun ResultScreen(
    uiState: CountryGameUiState,
    viewModel: CountryGameViewModel,
    onNavigateToQuestion: () -> Unit,
    onNavigateToScore: () -> Unit
) {
    val country = uiState.currentCountry
    val isCorrect = uiState.isAnswerCorrect ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isCorrect) {
            Text(
                text = "Correct!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            Text(
                text = "Wrong!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "The correct answer was: ${country?.name}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        country?.let {
            Image(
                painter = painterResource(id = it.flag),
                contentDescription = "Country Flag",
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
            )

            Text(
                text = "Capital: ${it.capital}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Famous for: ${it.feature}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (uiState.questionsAsked < 10) {
                Button(onClick = {
                    viewModel.nextQuestion()
                    onNavigateToQuestion()
                }) {
                    Text("Next Question")
                }
            } else {
                Button(onClick = onNavigateToScore) {
                    Text("See Final Score")
                }
            }
        }
    }
}