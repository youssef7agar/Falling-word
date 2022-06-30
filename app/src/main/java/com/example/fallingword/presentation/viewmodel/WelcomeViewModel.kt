package com.example.fallingword.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fallingword.local.Preferences
import com.example.fallingword.presentation.viewstate.WelcomeViewState
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    private val _viewState = MutableLiveData<WelcomeViewState>()
    val viewState: LiveData<WelcomeViewState>
        get() = _viewState

    private val highScore: String
        get() = preferences.getHighScore().toString()

    init {
        populateHighScore()
    }

    fun populateHighScore() {
        _viewState.postValue(
            WelcomeViewState(
                highScore = highScore
            )
        )
    }
}