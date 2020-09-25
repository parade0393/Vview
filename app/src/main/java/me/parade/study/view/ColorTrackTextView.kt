package me.parade.study.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author : parade
 * date : 2020/9/20
 * description :一个文字两种颜色
 */
class ColorTrackTextView : AppCompatTextView {
    private var mNormalPaint: Paint
    private var mChangePaint: Paint

    private var mCurrentProgress = 0.5F
    private lateinit var rect: Rect
    private var mDirection = Direction.LEFT_TO_RIGHT


    constructor(context: Context):this(context,null)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int):super(context, attrs,defStyleAttr){
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView)

        //文字系统默认颜色textColors.defaultColor
        val originColor = array.getColor(
            R.styleable.ColorTrackTextView_originalColor,
            textColors.defaultColor
        )
        val changeColor =
            array.getColor(R.styleable.ColorTrackTextView_changeColor, textColors.defaultColor)
        mNormalPaint = getPaintByColor(originColor)
        mChangePaint = getPaintByColor(changeColor)

        array.recycle()
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val middle = mCurrentProgress * width
        if (mDirection == Direction.LEFT_TO_RIGHT){//做变色右不变色
            Log.d("TAG", "onDraw: LEFT_TO_RIGHT")
            //绘制变色
            drawText(canvas,mChangePaint,0,middle.toInt())

            //绘制不变色
            drawText(canvas,mNormalPaint,middle.toInt(),width)
        }else{
            Log.d("TAG", "onDraw: RIGHT_TO_LEFT")
            drawText(canvas,mChangePaint,(width-middle).toInt(),width)

            //绘制不变色
            drawText(canvas,mNormalPaint,0,(width-middle).toInt())
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

    private fun drawText(canvas: Canvas?,paint: Paint,start:Int,end:Int){
        canvas?.save()
        val rect = Rect(start,0,end,height)
        //绘制不变色
        canvas?.clipRect(rect)
        val toString = text.toString()
       val  bounds = Rect()
        paint.getTextBounds(text,0,text.length,bounds)//文字最小区域
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

    fun setChangeColor(changeColor:Int){
        this.mChangePaint.color = changeColor
    }

    fun setOriginColor(originColor:Int){
        this.mNormalPaint.color = originColor
    }
}