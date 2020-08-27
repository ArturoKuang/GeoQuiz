package com.example.geoguesser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.security.AccessControlContext

private const val EXTRA_ANSWER_IS_TRUE =
    "com.example.geogusseer.answer_is_true"

const val EXTRA_CHEAT_NUM =
    "com.example.geogusseer.cheat_num"

const val EXTRA_ANSWER_SHOWN =
    "com.example.geogusseer.answer_shown"

private const val KEY_CHEAT = "cheat"
private const val MAX_NUM_CHEAT = 3

class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private var shownAnswer = false
    private var cheatNum = 0
    private lateinit var answerTextView: TextView
    private lateinit var apiTextView: TextView
    private lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        cheatNum = intent.getIntExtra(EXTRA_CHEAT_NUM, 0)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        apiTextView = findViewById(R.id.api_text_view)
        shownAnswer = savedInstanceState?.getBoolean(KEY_CHEAT, false) ?: false

        val apiText = "API Level ${Build.VERSION.SDK_INT}"
        apiTextView.text = apiText

        showAnswerButton.setOnClickListener {
            if (cheatNum >= MAX_NUM_CHEAT) {
                Toast.makeText(
                    this,
                    "You have cheated the maximum number of times!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            setAnswerText()
            cheatNum++
            shownAnswer = true
            setAnswerShowResult()
        }

        if (shownAnswer) {
            setAnswerText()
            setAnswerShowResult()
        }
    }

    private fun setAnswerText() {
        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        answerTextView.setText(answerText)
    }

    private fun setAnswerShowResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, shownAnswer)
            putExtra(EXTRA_CHEAT_NUM, cheatNum)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatNum: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CHEAT_NUM, cheatNum)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_CHEAT, shownAnswer)
    }
}