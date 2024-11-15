package ru.me.a24camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File


class MainActivity : AppCompatActivity() {
    private val REQUEST_TAKE_PHOTO = 1

    private lateinit var miniImage: ImageView
    private lateinit var fullImage: ImageView
    private var TAKE_PICTURE_REQUEST: Int = 1
    private lateinit var outputFileUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        miniImage = findViewById(R.id.miniImage)
        fullImage = findViewById(R.id.fullImage)

        val button_full_image: Button = findViewById(R.id.button_full_image)
        val button_bitmap: Button = findViewById(R.id.button_bitmap)

        button_full_image.setOnClickListener {
            try {
                saveFullImage()
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        button_bitmap.setOnClickListener {
            try {
                getThumbnailPicture()
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            TAKE_PICTURE_REQUEST ->{
                if(resultCode == Activity.RESULT_OK && data !== null){
                    if (data.hasExtra("data")) {
                        miniImage.setImageBitmap(data.extras?.get("data") as Bitmap)
                    }
                }
                if(resultCode == Activity.RESULT_OK && data == null){
                    fullImage.setImageDrawable(null)
                    fullImage.setImageURI(outputFileUri)
                }
            }
            else ->{
                Toast.makeText(this, "Wrong request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getThumbnailPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, TAKE_PICTURE_REQUEST)
    }

    private fun saveFullImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "test.jpg"
        )
        //outputFileUri = Uri.fromFile(file) --- устарело
        outputFileUri = FileProvider.getUriForFile(
            this@MainActivity,
            "ru.me.a24camera.provider", //(use your app signature + ".provider" )
            file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        startActivityForResult(intent, TAKE_PICTURE_REQUEST)
    }
}