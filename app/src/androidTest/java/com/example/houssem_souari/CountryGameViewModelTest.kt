package com.example.houssem_souari

import com.example.houssem_souari.data.Country
import com.example.houssem_souari.viewModel.CountryGameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CountryGameViewModelTest {

    private lateinit var viewModel: CountryGameViewModel

    @Before
    fun setup() {
        viewModel = CountryGameViewModel()
    }

    @Test
    fun initialState_isCorrect() {
        val uiState = viewModel.uiState.value

        assertEquals(0, uiState.score)
        assertEquals(1, uiState.questionsAsked) // First question is loaded in init
        assertNotNull(uiState.currentCountry)
        assertEquals("", uiState.currentGuess)
        assertNull(uiState.isAnswerCorrect)
        assertEquals(false, uiState.isGameOver)
    }

    @Test
    fun updateGuess_updatesCurrentGuess() {
        viewModel.updateGuess("Brazil")

        assertEquals("Brazil", viewModel.uiState.value.currentGuess)
    }

    @Test
    fun checkAnswer_correctAnswer_incrementsScore() {
        // Set a known country first
        val testCountry = Country("TestCountry", 0, "TestCapital", "TestFeature")

        // Manually set the currentCountry using reflection (for test purposes only)
        val field = CountryGameViewModel::class.java.getDeclaredField("_uiState")
        field.isAccessible = true
        val stateFlow = field.get(viewModel)
        val updateMethod = stateFlow.javaClass.getDeclaredMethod("update", Function1::class.java)
        updateMethod.isAccessible = true
        updateMethod.invoke(stateFlow, com.example.houssem_souari.viewModel.CountryGameUiState::copy.javaClass.methods.first { it.name == "copy" && it.parameterCount > 0 }.invoke(viewModel.uiState.value, testCountry, "", 0, 1, null, false))

        // Now test with our known country
        viewModel.updateGuess("TestCountry")
        viewModel.checkAnswer()

        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.score)
        assertEquals(true, uiState.isAnswerCorrect)
    }

    @Test
    fun checkAnswer_incorrectAnswer_doesNotIncrementScore() {
        viewModel.updateGuess("WrongCountry")
        viewModel.checkAnswer()

        val uiState = viewModel.uiState.value
        assertEquals(0, uiState.score)
        assertEquals(false, uiState.isAnswerCorrect)
    }

    @Test
    fun resetGame_resetsStateCorrectly() {
        // First, advance the game a bit
        viewModel.updateGuess("SomeGuess")
        viewModel.checkAnswer()
        viewModel.nextQuestion()

        // Now reset
        viewModel.resetGame()

        val uiState = viewModel.uiState.value
        assertEquals(0, uiState.score)
        assertEquals(1, uiState.questionsAsked) // First question is loaded in resetGame
        assertNotNull(uiState.currentCountry)
        assertEquals("", uiState.currentGuess)
        assertNull(uiState.isAnswerCorrect)
        assertEquals(false, uiState.isGameOver)
    }

    @Test
    fun nextQuestion_after10Questions_setsGameOver() {
        // Simulate answering 9 more questions (we start with 1)
        repeat(9) {
            viewModel.nextQuestion()
        }

        // This should be the 11th question, which should trigger game end
        viewModel.nextQuestion()

        assertTrue(viewModel.uiState.value.isGameOver)
    }
}
