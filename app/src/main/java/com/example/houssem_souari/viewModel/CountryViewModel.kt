package com.example.houssem_souari.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houssem_souari.data.Country
import com.example.houssem_souari.data.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountryGameViewModel : ViewModel() {
    private val repository = CountryRepository()
    private val allCountries = repository.getCountries()
    private var usedCountriesIndices = mutableSetOf<Int>()
    private var currentCountryIndex = -1

    private val _uiState = MutableStateFlow(CountryGameUiState())
    val uiState: StateFlow<CountryGameUiState> = _uiState.asStateFlow()

    init {
        nextQuestion()
    }

    fun nextQuestion() {
        if (usedCountriesIndices.size >= 10 || usedCountriesIndices.size >= allCountries.size) {
            _uiState.update { it.copy(isGameOver = true) }
            return
        }

        //this will pick a random country that hasn't been used yet
        do {
            currentCountryIndex = (0 until allCountries.size).random()
        } while (currentCountryIndex in usedCountriesIndices)

        usedCountriesIndices.add(currentCountryIndex)

        _uiState.update {
            it.copy(
                currentCountry = allCountries[currentCountryIndex],
                currentGuess = "",
                isAnswerCorrect = null,
                questionsAsked = usedCountriesIndices.size
            )
        }
    }

    fun updateGuess(guess: String) {
        _uiState.update { it.copy(currentGuess = guess) }
    }

    fun checkAnswer() {
        val isCorrect = _uiState.value.currentGuess.trim().equals(
            _uiState.value.currentCountry?.name, ignoreCase = true
        )

        if (isCorrect) {
            _uiState.update {
                it.copy(
                    score = it.score + 1,
                    isAnswerCorrect = true
                )
            }
        } else {
            _uiState.update { it.copy(isAnswerCorrect = false) }
        }
    }

    fun resetGame() {
        usedCountriesIndices.clear()
        _uiState.update {
            CountryGameUiState(
                score = 0,
                questionsAsked = 0,
                isGameOver = false
            )
        }
        nextQuestion()
    }
}

data class CountryGameUiState(
    val currentCountry: Country? = null,
    val currentGuess: String = "",
    val score: Int = 0,
    val questionsAsked: Int = 0,
    val isAnswerCorrect: Boolean? = null,
    val isGameOver: Boolean = false
)
