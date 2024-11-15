package ru.me.a14notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException


class MainActivity : AppCompatActivity() {
    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }
    private var counter = 101
    private lateinit var soundPool: SoundPool
    private lateinit var assetManager: AssetManager

    private var catSound: Int = 0
    private var streamID = 0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        val sound =
//            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/raw/glass_break.mp3")

        /*val sound = Uri.parse("android.resource://"+packageName+"/"+R.raw.glass_break)
        val ringURI =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)*/

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        assetManager = assets
        catSound = loadSound("glass_break.mp3")

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // Создаём уведомление
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Напоминание")
                .setContentText("Пора покормить кота")
                .setContentIntent(pendingIntent)
                .setLargeIcon(
                    BitmapFactory.decodeResource(getResources(),
                    R.drawable.akashi1)) // большая картинка
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setSound(sound)
                .addAction(R.drawable.icons_padlock, "Открыть", pendingIntent)
                .addAction(R.drawable.icons_replay, "Отказаться", pendingIntent)
                .addAction(R.drawable.icons_cat_footprint, "Другой вариант", pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                //notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
                notify(counter++, builder.build()) // посылаем уведомление
                playSound(catSound)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Channel"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = checkNotNull(
                getSystemService(
                    NotificationManager::class.java
                )
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onPause() {
        super.onPause()

        soundPool.release()
    }

    private fun playSound(sound: Int): Int {
        if (sound > 0) {
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