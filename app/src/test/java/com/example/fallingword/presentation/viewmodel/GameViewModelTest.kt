package com.example.fallingword.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fallingword.domain.model.Challenge
import com.example.fallingword.domain.model.Word
import com.example.fallingword.domain.usecase.GetNextChallengeUseCase
import com.example.fallingword.domain.usecase.GetWordsUseCase
import com.example.fallingword.factory.ChallengeFactory.makeChallenge
import com.example.fallingword.factory.WordFactory.makeWord
import com.example.fallingword.local.Preferences
import com.example.fallingword.presentation.viewstate.GameViewState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getWordsUseCase = mock<GetWordsUseCase>()
    private val getNextChallengeUseCase = mock<GetNextChallengeUseCase>()
    private val preferences = mock<Preferences>()
    private val viewModel by lazy {
        GameViewModel(
            getWordsUseCase = getWordsUseCase,
            getNextChallengeUseCase = getNextChallengeUseCase,
            preferences = preferences
        )
    }

    private val highScore = 13
    @Before
    fun start() {
        stubGetHighScore(highScore)
    }

    @Test
    fun`viewModel starts with Loading viewState`() {
        stubGetWords(Single.never())

        val expected = GameViewState.Loading

        assertEquals(
            expected,
            viewModel.viewState.value
        )
    }

    @Test
    fun`viewModel starts with loading the words`() {
        stubGetWords(Single.never())
        viewModel.hashCode()

        verify(getWordsUseCase).execute()
    }

    @Test
    fun`local variable words is populated with the list returned from the getWordsUseCase`() {
        val words = listOf(makeWord())
        stubGetWords(Single.just(words))
        val challenge = makeChallenge()
        stubGetNextChallenge(challenge)
        viewModel.hashCode()

        assertEquals(
            words,
            viewModel.words
        )
    }

    @Test
    fun`getNextChallengeUseCase is executed with the words list when fetched successfully`() {
        val words = listOf(makeWord())
        val challenge = makeChallenge()
        stubGetWords(Single.just(words))
        stubGetNextChallenge(challenge)
        viewModel.hashCode()

        verify(getNextChallengeUseCase).execute(words)
    }

    @Test
    fun`viewModel posts the fetched challenge content to the view state`() {
        val words = listOf(makeWord())
        val challenge = makeChallenge()
        stubGetWords(Single.just(words))
        stubGetNextChallenge(challenge)

        val expected = GameViewState.Gaming(
            secondsLeft = "9",
            highScore = highScore.toString(),
            currentScore = "0",
            englishWord = challenge.englishWOrd,
            spanishWord = challenge.spanishWord
        )

        assertEquals(
            expected,
            viewModel.viewState.value
        )
    }

    @Test
    fun`fetched challenge from the useCase is stored in the challenge local variable`() {
        val words = listOf(makeWord())
        val challenge = makeChallenge()
        stubGetWords(Single.just(words))
        stubGetNextChallenge(challenge)
        viewModel.hashCode()

        assertEquals(
            challenge,
            viewModel.challenge
        )
    }

    private fun stubGetWords(single: Single<List<Word>>) {
        whenever(getWordsUseCase.execute())
            .thenReturn(single)
    }

    private fun stubGetNextChallenge(challenge: Challenge) {
        whenever(getNextChallengeUseCase.execute(any()))
            .thenReturn(challenge)
    }

    private fun stubGetHighScore(highScore: Int) {
        whenever(preferences.getHighScore())
            .thenReturn(highScore)
    }
}