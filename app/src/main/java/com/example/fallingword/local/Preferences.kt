package com.example.fallingword.local

import android.content.Context
import com.example.fallingword.common.Constants.Companion.HIGH_SCORE
import com.example.fallingword.common.int
import javax.inject.Inject

class Preferences @Inject constructor(
    context: Context
) {

    private var highScoreLocal by context.getSharedPreferences(
        HIGH_SCORE,
        Context.MODE_PRIVATE
    ).int(key = { HIGH_SCORE })

    fun addHighScore(highScore: Int) {
        this.highScoreLocal = highScore
    }

    fun getHighScore(): Int {
        return this.highScoreLocal
    }
}