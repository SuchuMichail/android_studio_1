package ru.me.a17graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class Draw2D(context: Context) : View(context) {
    private val paint: Paint = Paint()
    private val rect: Rect = Rect()
    val res: Resources = this.resources
    private var bitmap: Bitmap = BitmapFactory.decodeResource(res, R.drawable.pinkhellokitty)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.CYAN // закрашиваем холст белым цветом
        }
        canvas.drawPaint(paint)

        paint.apply {
            isAntiAlias = true
            color = Color.YELLOW
        }

        canvas.drawCircle(950F, 150F, 100F, paint)

        paint.color = Color.GREEN

        canvas.drawRect(20F, 1050F, 1050F, 2100F, paint)

        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 40F
        }
        canvas.drawText("Лужайка только для котов", 40F, 1150F, paint)



        val x = 810F
        val y = 300F

        paint.apply {
            color = Color.GRAY
            style = Paint.Style.FILL
            textSize = 30F
        }

        val str2rotate = "Лучик солнца!"

        canvas.save()
        canvas.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        canvas.drawText(str2rotate, x, y, paint)

        canvas.restore()

        canvas.drawBitmap(
            bitmap, (width - bitmap.width).toFloat() - 100F, (height - bitmap.height
                    - 10).toFloat() - 350F, paint
        )
    }
}