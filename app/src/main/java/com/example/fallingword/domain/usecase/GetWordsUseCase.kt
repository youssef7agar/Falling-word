package com.example.fallingword.domain.usecase

import com.example.fallingword.domain.model.Word
import com.example.fallingword.domain.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Single<List<Word>> {
        return repository.getWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}