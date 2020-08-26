package com.example.geoguesser

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var correctAnswer = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_australlia, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, false),
        Question(R.string.question_asia, false),
        Question(R.string.question_mideast, true),
        Question(R.string.question_oceans, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].testResId

    var currentQuestionHasAnswered: Boolean
        get() = questionBank[currentIndex].hasAnswered
        set(value) {
            questionBank[currentIndex].hasAnswered = value
        }

    val size: Int
        get() = questionBank.size

    val allAnswered: Boolean
        get() {
            var answerCount = 0
            for (question in questionBank) {
                if (question.hasAnswered)
                    answerCount++
            }
            return answerCount == size
        }


    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1)
        if (currentIndex < 0)
            currentIndex = questionBank.size - 1
    }
}