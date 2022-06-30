package com.example.fallingword.domain.usecase

import com.example.fallingword.common.randomBoolean
import com.example.fallingword.common.randomInt
import com.example.fallingword.domain.model.Challenge
import com.example.fallingword.domain.model.Word
import javax.inject.Inject

class GetNextChallengeUseCase @Inject constructor() {

    fun execute(words: List<Word>): Challenge {
        val englishWordIndex = randomInt(words.size-1)
        val spanishWordIndex = if (randomBoolean()) englishWordIndex else randomInt(words.size-1)

        return Challenge(
            englishWOrd = words[englishWordIndex].englishWord,
            spanishWord = words[spanishWordIndex].spanishWord,
            status = spanishWordIndex == englishWordIndex
        )
    }
}