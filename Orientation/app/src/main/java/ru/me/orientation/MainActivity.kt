package ru.me.orientation

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var textViewCrows: TextView
    private lateinit var button: Button
    private var mCount: Int = 0

    companion object {
        const val KEY_COUNT = "COUNT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        textViewCrows = findViewById(R.id.textView_crows)
        button = findViewById(R.id.button)

        textView.text = getScreenOrientation()

        button.setOnClickListener {
            textViewCrows.text = "Я насчитал ${++mCount} ворон"
        }

        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt(KEY_COUNT, 0)
            textViewCrows.text = "Я насчитал $mCount ворон"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNT, mCount)
    }

    private fun getScreenOrientation(): String {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Портретная ориентация"
            Configuration.ORIENTATION_LANDSCAPE -> "Альбомная ориентация"
            else -> "Неопределённая ориентация"
        }
    }

/*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Проверяем ориентацию экрана
        val orientation = when(newConfig.orientation){
            Configuration.ORIENTATION_PORTRAIT -> "Portrait"
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            Configuration.ORIENTATION_UNDEFINED -> "Undefined"
            else -> "Error"
        }

        textView.text = "Orientation : $orientation"
    }*/

    /*
    private fun getRotateOrientation(): String {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> "Не поворачивали"
            Surface.ROTATION_90 -> "Повернули на 90 градусов по часовой стрелке"
            Surface.ROTATION_180 -> "Повернули на 180 градусов"
            Surface.ROTATION_270 -> "Повернули на 90 градусов против часовой стрелки"
            else -> "Не понятно"
        }
    }*/
}