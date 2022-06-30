package com.example.fallingword.domain.mapper

import com.example.fallingword.domain.model.Word
import com.example.fallingword.remote.model.WordRemote
import javax.inject.Inject

class WordMapper @Inject constructor() {

    fun map(input: List<WordRemote>): List<Word> {
        return input.map(::map)
    }

    private fun map(word: WordRemote): Word {
        assertEssentialParams(remote = word)

        return Word(
            englishWord = word.englishWord!!,
            spanishWord = word.spanishWord!!
        )
    }

    private fun assertEssentialParams(remote: WordRemote) {
        when {
            remote.englishWord == null -> {
                throw RuntimeException("The params: english word is missing in received object: $remote")
            }
            remote.spanishWord == null -> {
                throw RuntimeException("The params: spanish word is missing in received object: $remote")
            }
        }
    }
}