package ru.me.switchingbetweenscreens

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    private lateinit var textview_second_info: TextView
    companion object{
        const val THIEF = "ru.me.switchingbetweenscreens.THIEF"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        textview_second_info = findViewById(R.id.textViewSecond)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        var user = "ЖЫвотное"
        var gift = "дырку от бублика"
        user = intent.extras?.getString("username") ?: "ЖЫвотное"
        gift = intent.extras?.getString("gift") ?: "дырку от бублика"

        textview_second_info.text = "$user, вам передали $gift"

        radioGroup.setOnCheckedChangeListener{ _, optionId ->
            val answerIntent = Intent()
            when(optionId){
                R.id.radio_cat -> answerIntent.putExtra(THIEF, "Кот")
                R.id.radio_dog -> answerIntent.putExtra(THIEF, "Собака")
                R.id.radio_crow -> answerIntent.putExtra(THIEF, "Ворона")
            }
            setResult(RESULT_OK, answerIntent)
            finish()
        }

       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}