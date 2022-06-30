package com.example.fallingword.presentation.viewmodel

import android.os.CountDownTimer
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fallingword.R
import com.example.fallingword.domain.model.Challenge
import com.example.fallingword.domain.model.Word
import com.example.fallingword.domain.usecase.GetNextChallengeUseCase
import com.example.fallingword.domain.usecase.GetWordsUseCase
import com.example.fallingword.local.Preferences
import com.example.fallingword.presentation.viewstate.GameViewState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import timber.log.Timber
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val getNextChallengeUseCase: GetNextChallengeUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _viewState = MutableLiveData<GameViewState>()
    val viewState: LiveData<GameViewState>
        get() = _viewState

    private val highScore: Int
        get() = preferences.getHighScore()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var words: List<Word>
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var challenge: Challenge
    private var currentSessionScore = 0
    private val timePeriod = 10000L

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var timer: CountDownTimer? = null

    init {
        getWords()
    }

    fun getWords() {
        _viewState.postValue(GameViewState.Loading)

        getWordsUseCase.execute().subscribeWith(GetWordsSubscriber())
            .also(compositeDisposable::add)
    }

    private inner class GetWordsSubscriber : DisposableSingleObserver<List<Word>>() {
        override fun onSuccess(t: List<Word>) {
            words = t
            getNextChallenge()
        }

        override fun onError(e: Throwable) {
            Timber.w("Something went wrong while loading the words $e")
        }
    }

    private fun getNextChallenge() {
        challenge = getNextChallengeUseCase.execute(words)
        Timber.v("$challenge")
        _viewState.postValue(GameViewState.Gaming(
            secondsLeft = "9",
            highScore = highScore.toString(),
            currentScore = currentSessionScore.toString(),
            englishWord = challenge.englishWOrd,
            spanishWord = challenge.spanishWord
        ))
        startTimer()
    }

    fun clickOnCorrect() {
        stopTimer()
        if (challenge.status) {
            updateScore()
            getNextChallenge()
        } else {
            currentSessionScore = 0
            _viewState.postValue(GameViewState.Lost(
                message = R.string.wrong_choice_message,
                highScore = highScore.toString()
            ))
        }
    }

    fun clickOnIncorrect() {
        stopTimer()
        if (!challenge.status) {
            updateScore()
            getNextChallenge()
        } else {
            currentSessionScore = 0
            _viewState.postValue(GameViewState.Lost(
                message = R.string.wrong_choice_message,
                highScore = highScore.toString()
            ))
        }
    }

    private fun updateScore() {
        currentSessionScore++
        if (currentSessionScore > highScore) {
            preferences.addHighScore(currentSessionScore)
        }
    }

    private fun startTimer() {
        stopTimer()
        timer = object : CountDownTimer(timePeriod, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _viewState.postValue((viewState.value as GameViewState.Gaming).copy(
                    secondsLeft = (millisUntilFinished / 1000).toString()
                ))
            }

            override fun onFinish() {
                currentSessionScore = 0
                _viewState.postValue(GameViewState.Lost(
                    message = R.string.time_up,
                    highScore = highScore.toString()
                ))
            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}