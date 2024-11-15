package ru.me.a15converter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val meterRadioButton: RadioButton = findViewById(R.id.radio_button_meter)
        val inputEditText: EditText = findViewById(R.id.editText)

        val button: Button = findViewById(R.id.button_converter)
        button.setOnClickListener {
            if (inputEditText.text.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Введите длину кота",
                    Toast.LENGTH_LONG).show()
            }
            else {
                val inputValue = inputEditText.text.toString().toFloat()
                if (meterRadioButton.isChecked) {
                    inputEditText.setText(convertParrotToMeter(inputValue).toString())
                } else {
                    inputEditText.setText(convertMeterToParrot(inputValue).toString())
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Конвертируем в метры
    private fun convertParrotToMeter(parrot: Float): Float = (parrot / 7.6).toFloat()

    // Конвертируем в попугаи
    private fun convertMeterToParrot(meter: Float): Float = (meter * 7.6).toFloat()
}