package com.example.geoguesser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : Button
    private lateinit var questionView : TextView

    private val questionBank = listOf(
        Question(R.string.question_australlia, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, false),
        Question(R.string.question_asia, false),
        Question(R.string.question_mideast, true),
        Question(R.string.question_oceans, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            val toast = Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT)

            toast.setGravity(Gravity.TOP,0,0)
            toast.show()
        }

        falseButton.setOnClickListener { view: View ->
            val toast = Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT)

            toast.setGravity(Gravity.TOP,0, 0)
            toast.show()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            update()
        }

        update()
    }


    private fun update() {
        val questionTextResId = questionBank[currentIndex].testResId
        question_text_view.setText(questionTextResId)
    }
}