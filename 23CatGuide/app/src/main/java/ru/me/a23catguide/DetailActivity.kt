package ru.me.a23catguide

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        val webView: WebView = findViewById(R.id.webView)

        val intent = intent
        //получаем строку и формируем имя ресурса
        val resName = "n" + intent.getIntExtra("title", 0)

        val context: Context = baseContext

        //читаем текстовый файл из ресурсов по имени
        val text: String = readRawTextFile(
            context, resources.getIdentifier(
                resName,
                "raw", "ru.me.a23catguide"
            )
        )

        webView.loadDataWithBaseURL(null, text, "text/html", "en_US", null)
    }

    //читаем текст из raw-ресурсов
    private fun readRawTextFile(context: Context, resId: Int): String {
        val inputStream: InputStream = context.resources.openRawResource(resId)
        val inputReader = InputStreamReader(inputStream)
        val buffReader = BufferedReader(inputReader)
        var line: String?
        val builder = StringBuilder()
        try {
            while (buffReader.readLine().also { line = it } != null) {
                builder.append(line)
                builder.append("\n")
            }
        } catch (e: IOException) {
            return e.localizedMessage
        }
        return builder.toString()
    }
}