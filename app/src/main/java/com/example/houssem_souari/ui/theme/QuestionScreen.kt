package com.example.houssem_souari.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.houssem_souari.viewModel.CountryGameUiState
import com.example.houssem_souari.viewModel.CountryGameViewModel

@Composable
fun QuestionScreen(
    uiState: CountryGameUiState,
    viewModel: CountryGameViewModel,
    onNavigateToResult: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Question ${uiState.questionsAsked} of 10",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Which country has this flag?",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        uiState.currentCountry?.let { country ->
            Image(
                painter = painterResource(id = country.flag),
                contentDescription = "Country Flag",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }

        OutlinedTextField(
            value = uiState.currentGuess,
            onValueChange = { viewModel.updateGuess(it) },
            label = { Text("Enter country name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.checkAnswer()
                    onNavigateToResult()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Button(
            onClick = {
                viewModel.checkAnswer()
                onNavigateToResult()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Check Answer")
        }

        Text(
            text = "Score: ${uiState.score}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}