package ru.me.a20keyboard

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editText1 = findViewById(R.id.editTextText)
        editText2 = findViewById(R.id.editTextText2)
        editText3 = findViewById(R.id.editTextText3)
        editText4 = findViewById(R.id.editTextText4)

        editText2.inputType = InputType.TYPE_CLASS_PHONE
        editText3.inputType = InputType.TYPE_CLASS_DATETIME
        editText4.inputType = InputType.TYPE_CLASS_NUMBER

        editText1.setOnEditorActionListener(this)
        editText2.setOnEditorActionListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Подтверждение")
            setMessage("Вы уверены, что хотите выйти из программы?")

            setPositiveButton("Таки да") { _, _ ->
                super.onBackPressed()
            }

            setNegativeButton("Нет"){_, _ ->
                // if user press no, then return the activity
                Toast.makeText(this@MainActivity, "Thank you",
                    Toast.LENGTH_LONG).show()
            }
            setCancelable(true)
        }.create().show()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // обрабатываем нажатие кнопки поиска
            if (!editText1.getText().toString().equals("cat")) {
                Toast.makeText(this, "Не буду ничего искать!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Отыщем кота!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (actionId == EditorInfo.IME_ACTION_GO) {
            // обрабатываем нажатие кнопки GO
            if (!editText2.getText().toString().equals("cat")) {
                Toast.makeText(this, "Нет кота!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Ура, кот!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}