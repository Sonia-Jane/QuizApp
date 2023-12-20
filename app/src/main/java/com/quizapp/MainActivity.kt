package com.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * Cette fonction est créée automatiquement par Android lors de la création de la classe d'activité.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Cet appel au constructeur parent
        super.onCreate(savedInstanceState)
        // Ceci est utilisé pour aligner la vue XML sur cette classe
        setContentView(R.layout.activity_main)

        // Pour masquer la barre d'état.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btn_start.setOnClickListener {

            val intent = Intent(this@MainActivity, Navigation::class.java)
                // TODO (ÉTAPE 2 : Transmettez le nom via l'intention en utilisant la variable constante que nous avons créée)
                startActivity(intent)
                finish()
        }
    }
}