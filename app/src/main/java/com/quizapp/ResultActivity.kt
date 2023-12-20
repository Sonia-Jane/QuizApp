package com.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // TODO (ÉTAPE 6 : Masquez la barre d'état, obtenez les détails de l'intention et définissez-la sur l'interface utilisateur. Et ajoutez également un événement de clic au bouton de fin.)
        // START
        // Masquer la barre d'état.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val totalQuestions = intent.getIntExtra(ConstantsMedium.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(ConstantsMedium.CORRECT_ANSWERS, 0)

        tv_score.text = "Your Score is $correctAnswers out of $totalQuestions."

        if (correctAnswers <= 4) {
            iv_trophy.setImageResource(R.drawable.sad_emoticon)
            tv_congratulations.text = "Hey, Try again!"
        }

        btn_finish.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }
        // END
    }
}