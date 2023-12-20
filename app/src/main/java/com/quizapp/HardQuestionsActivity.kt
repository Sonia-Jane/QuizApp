package com.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class HardQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1 // Position par défaut et première question
    private var mQuestionsList: ArrayList<QuestionHard>? = null

    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0

    // TODO (STEP 3: Create a variable for getting the name from intent.)
    // START
    private var mUserName: String? = null
    // END

    /**
     * Cette fonction est créée automatiquement par Android lors de la création de la classe d'activité.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Appel au constructeur parent
        super.onCreate(savedInstanceState)
        // Ceci est utilisé pour aligner la vue XML sur cette classe
        setContentView(R.layout.activity_quiz_hard_questions)

        // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
        // START
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        // END

        //retour a l'ecran de navigation
        btn_previous.setOnClickListener {
            // Lancer une nouvelle activité pour les questions de niveau intermédiaire
            val intent = Intent(this, Navigation::class.java)
            startActivity(intent)
        }

        mQuestionsList = ConstantsHard.getQuestions()

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.tv_option_one -> {

                selectedOptionView(tv_option_one, 1)
            }

            R.id.tv_option_two -> {

                selectedOptionView(tv_option_two, 2)
            }

            R.id.btn_submit -> {

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {

                            // TODO (ÉTAPE 5 : Supprimez maintenant le message toast et lancez l'écran de résultats que nous avons créé et transmettez-lui également le nom d'utilisateur et les détails du score.)
                            // START
                            val intent =
                                Intent(this@HardQuestionsActivity, ResultActivity::class.java)
                            intent.putExtra(ConstantsHard.USER_NAME, mUserName)
                            intent.putExtra(ConstantsHard.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(ConstantsHard.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // Pour vérifier si la réponse est fausse
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else {
                        mCorrectAnswers++
                    }

                    // pour les reponses correctes
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    /**
     *Une fonction pour définir la question sur les composants de l'interface utilisateur.
     */
    private fun setQuestion() {

        val question = mQuestionsList!!.get(mCurrentPosition - 1) // Obtenir la question de la liste à l'aide de la position actuelle.

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
    }

    /**
     * Une fonction pour définir la vue de la vue des options sélectionnées.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@HardQuestionsActivity,
            R.drawable.selected_option_border_bg
        )
    }

    /**
     *Une fonction pour définir l'affichage des options par défaut lorsque la nouvelle question est chargée ou lorsque la réponse est resélectionnée.
     */
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@HardQuestionsActivity,
                R.drawable.default_option_border_bg
            )
        }
    }

    /**
     * Une fonction d'affichage des réponses qui est utilisée pour mettre en évidence la réponse qui est fausse ou correcte.
     */
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(
                    this@HardQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this@HardQuestionsActivity,
                    drawableView
                )
            }
        }
    }
}