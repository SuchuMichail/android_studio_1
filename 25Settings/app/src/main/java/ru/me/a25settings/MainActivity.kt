package ru.me.a25settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var counter: Int = 0
    private lateinit var prefs: SharedPreferences
    private val APP_PREFERENCES_COUNTER = "counter"
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        prefs =
            getSharedPreferences("settings", Context.MODE_PRIVATE)

        textView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)
        val button_null: Button = findViewById(R.id.button_null)

        button.setOnClickListener{
            textView.text = "Я насчитал ${++counter} ворон"
        }

        button_null.setOnClickListener{
            counter = 0
            textView.text = "Я насчитал ${counter} ворон"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()

        // Запоминаем данные
        val editor = prefs.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, counter).apply()
    }

    override fun onResume() {
        super.onResume()

        if(prefs.contains(APP_PREFERENCES_COUNTER)){
            // Получаем число из настроек
            counter = prefs.getInt(APP_PREFERENCES_COUNTER, 0)
            // Выводим на экран данные из настроек
            textView.text = "Я насчитал $counter ворон"
        }
    }
}