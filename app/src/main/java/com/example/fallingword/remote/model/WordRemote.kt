package com.example.fallingword.remote.model

import com.google.gson.annotations.SerializedName

data class WordRemote(
    @SerializedName("text_eng") val englishWord: String?,
    @SerializedName("text_spa") val spanishWord: String?
)