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

    private var mCurrentProgress = 0.0F
    private lateinit var rect: Rect
    private var mDirection = Direction.LEFT_TO_RIGHT

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
        val middle = mCurrentProgress * width
        if (mDirection == Direction.LEFT_TO_RIGHT){//做变色右不变色
            //绘制变色
            drawText(canvas,mChangePaint,0F,middle)

            //绘制不变色
            drawText(canvas,mNormalPaint,middle,width.toFloat())
        }else{
            drawText(canvas,mChangePaint,width-middle,width.toFloat())

            //绘制不变色
            drawText(canvas,mNormalPaint,0F,width-middle)
        }

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

    fun setProgress(progress:Float){
        this.mCurrentProgress = progress
        invalidate()
    }

    fun setDirection(direction: Direction){
        this.mDirection = direction
    }


    enum class Direction{
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }
}