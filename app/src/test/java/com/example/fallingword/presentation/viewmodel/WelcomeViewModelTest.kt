package com.example.fallingword.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fallingword.local.Preferences
import com.example.fallingword.presentation.viewstate.WelcomeViewState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class WelcomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val preferences = mock<Preferences>()
    private val viewModel by lazy {
        WelcomeViewModel(
            preferences = preferences
        )
    }

    @Test
    fun`viewModel starts with high score in view state`() {
        val highScore = 13
        val expected = WelcomeViewState(highScore = highScore.toString())
        stubGetHighScore(highScore)

        assertEquals(
            expected,
            viewModel.viewState.value
        )
    }

    private fun stubGetHighScore(highScore: Int) {
        whenever(preferences.getHighScore())
            .thenReturn(highScore)
    }
}