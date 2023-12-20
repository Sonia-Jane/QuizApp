package com.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*

class Navigation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        // Trouvez les cartes de niveau dans la mise en page
        val easyCard = findViewById<CardView>(R.id.easy_level_card)
        val intermediateCard = findViewById<CardView>(R.id.intermediate_level_card)
        val hardCard = findViewById<CardView>(R.id.hard_level_card)

        // Définir des écouteurs de clic pour chaque carte de niveau
        easyCard.setOnClickListener {
            // Lancez une nouvelle activité pour les questions de niveau facile
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            startActivity(intent)
        }

        intermediateCard.setOnClickListener {
            // Lancer une nouvelle activité pour les questions de niveau intermédiaire
            val intent = Intent(this, MediumQuestionsActivity::class.java)
            startActivity(intent)
        }

        hardCard.setOnClickListener {
            // Lancer une nouvelle activité pour les questions de niveau difficile
            val intent = Intent(this, HardQuestionsActivity::class.java)
            startActivity(intent)
        }
    }
}