package com.example.fallingword.factory

import com.example.fallingword.common.randomString
import com.example.fallingword.domain.model.Word
import com.example.fallingword.remote.model.WordRemote

object WordFactory {

    fun makeWordRemote() = WordRemote(
        englishWord = randomString(),
        spanishWord = randomString()
    )

    fun makeWord() = Word(
        englishWord = randomString(),
        spanishWord = randomString()
    )
}