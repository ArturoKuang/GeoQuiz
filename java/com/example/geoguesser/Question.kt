package com.example.geoguesser

import androidx.annotation.StringRes

data class Question(
    @StringRes val testResId: Int,
    val answer: Boolean,
    var hasAnswered: Boolean = false
) {

}