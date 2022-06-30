package com.example.fallingword.domain.usecase

import com.example.fallingword.common.randomBoolean
import com.example.fallingword.common.randomInt
import com.example.fallingword.domain.model.Challenge
import com.example.fallingword.domain.model.Word
import javax.inject.Inject
/**
 * Here we pick a random index for the english word
 * And then we have a 50/50 chance the spanish word will be the same as the english word
 * if the random boolean is true then it'll be the same as it is, if false then we pick a new
 * random index for the spanish word. we then publish our challenge model.
 * */
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