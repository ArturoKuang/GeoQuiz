package com.example.geoguesser


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionView: TextView

    private var incorrectScore = 0;
    private var correctScore = 0;

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            update()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            update()
        }

        update()
    }

    private fun checkAnswer(userAnswer : Boolean) {
        if (quizViewModel.currentQuestionHasAnswered)
            return;

        quizViewModel.currentQuestionHasAnswered = true

        val messageResId = if (userAnswer == quizViewModel.currentQuestionAnswer) {
            quizViewModel.correctAnswer++;
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

    }

    private fun getScore(): Double {
        return quizViewModel.correctAnswer.toDouble() / quizViewModel.size.toDouble()
    }

    private fun update() {
        question_text_view.setText(quizViewModel.currentQuestionText)

        val scorePercent = getScore()
        if (quizViewModel.allAnswered) {
            val percentage = NumberFormat.getPercentInstance().format(scorePercent)
            Toast.makeText(this, "You scored $percentage", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(KEY_INDEX, "onSavedInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}