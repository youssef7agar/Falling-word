package com.example.fallingword.presentation.viewstate

sealed class GameViewState {

    object Loading : GameViewState()

    data class Gaming(
        val secondsLeft: String,
        val highScore: String,
        val currentScore: String,
        val englishWord: String,
        val spanishWord: String
    ) : GameViewState()

    data class Lost(
        val message: Int,
        val highScore: String
    ) : GameViewState()
}