package com.example.houssem_souari

import com.example.houssem_souari.data.Country
import com.example.houssem_souari.viewModel.CountryGameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// My first test for the Country Game app!
class CountryViewModelTest {

    // Creating our ViewModel that we want to test
    private val viewModel = CountryGameViewModel()

    // Test if the game starts with a score of 0
    @Test
    fun testInitialScoreIsZero() {
        // Get the current score from our ViewModel
        val score = viewModel.uiState.value.score

        // Check if it equals 0
        assertEquals("Initial score should be 0", 0, score)
    }

    // Test if the game starts with question number 1
    @Test
    fun testInitialQuestionIsOne() {
        // Get the current question number from our ViewModel
        val questionNumber = viewModel.uiState.value.questionsAsked

        // Check if it equals 1
        assertEquals("First question should be question 1", 1, questionNumber)
    }

    // Test if we can update our guess
    @Test
    fun testUpdateGuess() {
        // Update the guess to "Japan"
        viewModel.updateGuess("Japan")

        // Check if our guess was updated correctly
        assertEquals("Guess should be updated to Japan", "Japan", viewModel.uiState.value.currentGuess)
    }

    // Test what happens when we restart the game
    @Test
    fun testResetGame() {
        // First let's make a guess and increase the score
        viewModel.updateGuess("TestCountry")

        // Make up a correct answer function to increase score (this wouldn't be in a real test)
        val field = viewModel.javaClass.getDeclaredField("_uiState")
        field.isAccessible = true
        val state = viewModel.uiState.value.copy(score = 5, questionsAsked = 6)
        field.set(viewModel, androidx.lifecycle.MutableLiveData(state))

        // Now reset the game
        viewModel.resetGame()

        // Check if everything goes back to initial values
        assertEquals("Score should be reset to 0", 0, viewModel.uiState.value.score)
        assertEquals("Question should be reset to 1", 1, viewModel.uiState.value.questionsAsked)
        assertEquals("Guess should be empty", "", viewModel.uiState.value.currentGuess)
    }

    // Test that the current country is not null
    @Test
    fun testCurrentCountryExists() {
        val country = viewModel.uiState.value.currentCountry

        // Just make sure we have a country loaded
        assertTrue("Current country should not be null", country != null)
    }
}