package ru.me.switchingbetweenscreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.me.switchingbetweenscreens.SecondActivity.Companion.THIEF

class MainActivity : AppCompatActivity() {
    private lateinit var editAddress: EditText
    private lateinit var editGift: EditText
    private lateinit var textRobber: TextView
    companion object {
        const val REQUEST_CHOOSE_THIEF = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editAddress = findViewById(R.id.editAddress)
        editGift = findViewById(R.id.editGift)
        textRobber = findViewById(R.id.robber)

        var imageButton: ImageButton = findViewById(R.id.imageButton)
        var buttonToSecond: Button = findViewById(R.id.button)

        imageButton.setOnClickListener{
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        buttonToSecond.setOnClickListener{
            val intentSecond = Intent(this@MainActivity, SecondActivity::class.java)
            intentSecond.putExtra("username", editAddress.text.toString())
            intentSecond.putExtra("gift", editGift.text.toString())
            startActivityForResult(intentSecond, REQUEST_CHOOSE_THIEF)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if(resultCode == REQUEST_CHOOSE_THIEF){
//            if(resultCode == Activity.RESULT_OK){
//                val thiefName = data?.getStringExtra(THIEF)
//                textRobber.text = thiefName
//            }
//            else{
//                textRobber.text = ""
//            }
//        }

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CHOOSE_THIEF -> {
                    val thiefName = data?.getStringExtra(THIEF)
                    textRobber.text = thiefName.toString()
                }
            }
        }
        else{
            textRobber.text = ""
        }
    }
}