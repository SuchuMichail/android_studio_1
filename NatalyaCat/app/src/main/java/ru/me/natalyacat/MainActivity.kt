package ru.me.natalyacat

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val ultraText: TextView = findViewById(R.id.text_ultra)
        val tiredText: TextView = findViewById(R.id.text_tired)
        val hmText: TextView = findViewById(R.id.text_hm)
        val dangerousText: TextView = findViewById(R.id.text_dangerous)
        val rightBottomImage: ImageView = findViewById(R.id.right_bottom_image)

        rightBottomImage.setOnClickListener{
            val phrases = listOf(
                "Лучший",
                "Ну и ну",
                "Фантазёр",
                "Наруто",
                "Крутой"
            )

            val shuffledList = phrases.shuffled()
            ultraText.text = shuffledList[0]
            tiredText.text = shuffledList[1]
            hmText.text = shuffledList[2]
            dangerousText.text = shuffledList[3]
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}