package com.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_medium_questions.*

class MediumQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1 // Position par défaut et première question
    private var mQuestionsList: ArrayList<QuestionMedium>? = null

    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0

    // TODO (ÉTAPE 3 : Créez une variable pour obtenir le nom à partir de l'intention.)
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
        setContentView(R.layout.activity_quiz_medium_questions)

        // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
        // START
        mUserName = intent.getStringExtra(ConstantsMedium.USER_NAME)
        // END

        //Retour à l'écran de navigation
        btn_previous.setOnClickListener {
            // Lancer une nouvelle activité pour les questions de niveau intermédiaire
            val intent = Intent(this, Navigation::class.java)
            startActivity(intent)
        }

        mQuestionsList = ConstantsMedium.getQuestions()

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
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

            R.id.tv_option_three -> {

                selectedOptionView(tv_option_three, 3)
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
                                Intent(this@MediumQuestionsActivity, ResultActivity::class.java)
                            intent.putExtra(ConstantsMedium.USER_NAME, mUserName)
                            intent.putExtra(ConstantsMedium.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(ConstantsMedium.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else {
                        mCorrectAnswers++
                    }

                    // C'est pour la bonne réponse
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
     * Une fonction pour définir la question sur les composants de l'interface utilisateur.
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
        tv_option_three.text = question.optionThree
    }

    /**
     *Une fonction pour définir la vue de la vue des options sélectionnées
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@MediumQuestionsActivity,
            R.drawable.selected_option_border_bg
        )
    }

    /**
     * Une fonction pour définir l'affichage des options par défaut lorsque la nouvelle question est chargée ou lorsque la réponse est resélectionnée.
     */
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@MediumQuestionsActivity,
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
                    this@MediumQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this@MediumQuestionsActivity,
                    drawableView
                )
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                    this@MediumQuestionsActivity,
                    drawableView
                )
            }
        }
    }
}