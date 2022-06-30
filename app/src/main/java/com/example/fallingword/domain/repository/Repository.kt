package com.example.fallingword.domain.repository

import com.example.fallingword.domain.mapper.WordMapper
import com.example.fallingword.domain.model.Word
import com.example.fallingword.remote.api.ApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val wordMapper: WordMapper
) {

    fun getWords(): Single<List<Word>> {
        return apiService.getWords().map(wordMapper::map)
    }
}