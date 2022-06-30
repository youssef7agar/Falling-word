package com.example.fallingword.domain.repository

import com.example.fallingword.domain.mapper.WordMapper
import com.example.fallingword.domain.model.Word
import com.example.fallingword.domain.repository.Repository
import com.example.fallingword.factory.WordFactory.makeWord
import com.example.fallingword.factory.WordFactory.makeWordRemote
import com.example.fallingword.remote.api.ApiService
import com.example.fallingword.remote.model.WordRemote
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class RepositoryTest {

    private val apiService = mock<ApiService>()
    private val wordMapper = mock<WordMapper>()
    private val repository = Repository(
        apiService = apiService,
        wordMapper = wordMapper
    )

    @Test
    fun`repository calls api when getWords is called`() {
        stubGetWords(Single.never())

        repository.getWords()

        verify(apiService).getWords()
    }

    @Test
    fun`getWords returns data when all goes well`() {
        val wordsRemote = listOf(makeWordRemote())
        val words = listOf(makeWord())

        stubGetWords(Single.just(wordsRemote))
        stubMapWords(words)

        val testObserver = repository.getWords().test()
        testObserver.assertValue(words)
    }

    @Test
    fun`getWords returns an error when something goes wrong`() {
        val exception = Throwable()

        stubGetWords(Single.error(exception))

        val testObserver = repository.getWords().test()
        testObserver.assertError(exception)
    }

    private fun stubGetWords(single: Single<List<WordRemote>>) {
        whenever(apiService.getWords())
            .thenReturn(single)
    }

    private fun stubMapWords(list: List<Word>) {
        whenever(wordMapper.map(any()))
            .thenReturn(list)
    }
}