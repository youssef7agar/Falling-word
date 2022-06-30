package com.example.fallingword.remote.api

import com.example.fallingword.common.Constants.Companion.WORDS_API_ENDPOINT
import com.example.fallingword.remote.model.WordRemote
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET(WORDS_API_ENDPOINT)
    fun getWords(): Single<List<WordRemote>>
}