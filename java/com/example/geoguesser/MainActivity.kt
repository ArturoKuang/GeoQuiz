package com.example.geoguesser


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australlia, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, false),
        Question(R.string.question_asia, false),
        Question(R.string.question_mideast, true),
        Question(R.string.question_oceans, true)
    )

    private var currentIndex = 0;
    private var incorrectScore = 0;
    private var correctScore = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate(Bundle?) called")

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
            currentIndex = (currentIndex + 1) % questionBank.size
            update()
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1)
            if(currentIndex < 0)
                currentIndex = questionBank.size - 1
            update()
        }

        update()
    }

    private fun checkAnswer(userAnswer : Boolean) {
        val question = questionBank[currentIndex];
        if (question.hasAnswered)
            return;

        question.hasAnswered = true;

        val correctAnswer = question.answer

        val messageResId = if (userAnswer == correctAnswer) {
            correctScore++
            R.string.correct_toast
        } else {
            incorrectScore++
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

    }

    private fun getScore(): Double {
        val total = incorrectScore + correctScore;
        if (total == questionBank.size) {
            return correctScore.toDouble() / total.toDouble()
        }

        return -1.0
    }

    private fun update() {
        val questionTextResId = questionBank[currentIndex].testResId
        question_text_view.setText(questionTextResId)

        val scorePercent = getScore()
        if (scorePercent != -1.0) {
            //Log.d(TAG, scorePercent.toString())
            val percentage = NumberFormat.getPercentInstance().format(scorePercent)
            Toast.makeText(this, "You scored $percentage", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}