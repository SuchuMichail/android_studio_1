package ru.me.a10sounds

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private lateinit var assetManager: AssetManager

    private var glassSound: Int = 0
    private var akashiSound: Int = 0
    private var glockSound: Int = 0
    private var explosionSound: Int = 0

    private var streamID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val akashiImage: ImageView = findViewById(R.id.akashi_image)
        val explosionImage: ImageView = findViewById(R.id.explosion_image)
        val glockImage: ImageView = findViewById(R.id.glock_image)
        val glassImage: ImageView = findViewById(R.id.glass_image)

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        assetManager = assets
        glassSound = loadSound("glass_break.mp3")
        akashiSound = loadSound("moving_in_water.mp3")
        glockSound = loadSound("pistol_shot.mp3")
        explosionSound = loadSound("small_explosion.mp3")

        akashiImage.setOnClickListener {
            Log.w("ARARARARARAR", "try to play sound")
            playSound(akashiSound) }
        explosionImage.setOnClickListener { playSound(explosionSound) }
        glockImage.setOnClickListener { playSound(glockSound) }
        glassImage.setOnClickListener { playSound(glassSound) }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()

        soundPool.release()
    }


    private fun playSound(sound: Int): Int {
        if (sound > 0) {
            Log.w("YEYEYEYEYE","tryyyyy")
            streamID = soundPool.play(sound, 1F, 1F, 1, 0, 1F)
        }
        return streamID
    }

    private fun loadSound(fileName: String): Int {
        val afd: AssetFileDescriptor = try {
            application.assets.openFd(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Meow", "Не могу загрузить файл $fileName")

            return -1
        }
        return soundPool.load(afd, 1)
    }
}