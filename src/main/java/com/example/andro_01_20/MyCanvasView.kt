package com.example.andro_01_20

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.concurrent.thread
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class MyCanvasView(context: Context) : View(context) {

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var i=360

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
        //if (::extraBitmap.isInitialized) extraBitmap.recycle()
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        canvas.drawBitmap(extraBitmap, 0f, 0f, null)

        extraCanvas.drawText ("HOLA",((width-120)/3).toFloat(),((height+60)/2).toFloat() , paintText)

        coRoutine()

    }

    private fun coRoutine(){

        thread(start = true) {
            Thread.sleep(35) //35
            drawLines()
            clearRoutine()
        }

    }

    private fun clearRoutine(){

        thread(start = true) {
            Thread.sleep(35)    //30
            extraBitmap.eraseColor(backgroundColor)

        }
    }

    private fun drawLines(){

        var gradosI : Float = i.toFloat() / 180.0f * PI.toFloat()

        var x: Float = getXCircle(gradosI)
        var y: Float = getYCircle(gradosI)

        if (i in 0..91)
            y = -y
        else if (i in 91..180) {
            x=-x
            y=-y
        }else if (i in 181..270)
            x = -x

        x += width
        y += height

        paintCircle.setShader(RadialGradient(x/2, y/2, 8f,Color.CYAN,Color.TRANSPARENT,Shader.TileMode.CLAMP))

        extraCanvas.drawCircle (x/2, y/2,9f, paintCircle)

        invalidate()

        if (i>0) i-=2
        else i=360

    }

    private fun getXCircle(grados : Float) : Float{
        return abs(500f * cos(grados))
    }

    private fun getYCircle(grados : Float) : Float{
        return abs(500f * sin(grados))
    }

    private val paintText = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        strokeWidth = 3.0f
        textSize = 120.0f
    }

    private val paintCircle = Paint().apply {
        style = Paint.Style.FILL
    }

}