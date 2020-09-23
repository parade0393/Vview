package me.parade.study.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author : parade
 * date : 2020/9/20
 * description :一个文字两种颜色
 */
class ColorTrackTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    private lateinit var mNormalPaint: Paint
    private lateinit var mChangePaint: Paint

    private val mCurrentProgress = 0.5F
    private lateinit var rect: Rect

    init {
        attrs?.let {
            val array =
                context.obtainStyledAttributes(it, R.styleable.ColorTrackTextView)

            //文字系统默认颜色textColors.defaultColor
            val originColor = array.getColor(
                R.styleable.ColorTrackTextView_originalColor,
                textColors.defaultColor
            )
            val changeColor =
                array.getColor(R.styleable.ColorTrackTextView_changeColor, textColors.defaultColor)
            mNormalPaint = getPaintByColor(originColor)
            mChangePaint = getPaintByColor(changeColor)

            rect = Rect()

            array.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        val middle = mCurrentProgress * width

       /* canvas?.save()
        //绘制不变色
        canvas?.clipRect(0F,0F,middle,height.toFloat())
        val toString = text.toString()
        mNormalPaint.getTextBounds(text,0,text.length,rect)//文字最小区域
        val fontMetrics = mNormalPaint.fontMetrics
        val fl = height / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom//文字baseline
        canvas?.drawText(toString,(width/2-rect.width()/2).toFloat(),fl,mNormalPaint)
        canvas?.restore()*/
        drawText(canvas,mNormalPaint,0F,mCurrentProgress*width)

        //绘制变色
        /*canvas?.save()
        canvas?.clipRect(middle,0F,width.toFloat(),height.toFloat())
        canvas?.drawText(toString,(width/2-rect.width()/2).toFloat(),fl,mChangePaint)
        canvas?.restore()*/
        drawText(canvas,mChangePaint,mCurrentProgress*width,width.toFloat())


    }

    private fun getPaintByColor(colorRes: Int): Paint {
        return Paint().apply {
            color = colorRes
            isAntiAlias = true
            isDither = true
            textSize = this@ColorTrackTextView.textSize
        }
    }

    private fun drawText(canvas: Canvas?,paint: Paint,start:Float,end:Float){

        canvas?.save()
        //绘制不变色
        canvas?.clipRect(start,0F,end,height.toFloat())
        val toString = text.toString()
        paint.getTextBounds(text,0,text.length,rect)//文字最小区域
        val fontMetrics = paint.fontMetrics
        val fl = height / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom//文字baseline
        canvas?.drawText(toString,(width/2-rect.width()/2).toFloat(),fl,paint)
        canvas?.restore()
    }
}