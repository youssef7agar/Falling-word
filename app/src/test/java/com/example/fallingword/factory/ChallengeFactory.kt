package com.example.fallingword.factory

import com.example.fallingword.common.randomBoolean
import com.example.fallingword.common.randomString
import com.example.fallingword.domain.model.Challenge

object ChallengeFactory {

    fun makeChallenge() = Challenge(
        englishWOrd = randomString(),
        spanishWord = randomString(),
        status = randomBoolean()
    )
}