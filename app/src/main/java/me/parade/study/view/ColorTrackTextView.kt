package me.parade.study.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
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
        array.recycle()
        mNormalPaint = getPaintByColor(originColor)
        mChangePaint = getPaintByColor(changeColor)
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val middle = (mCurrentProgress * width ).toInt()
        if (mDirection == Direction.LEFT_TO_RIGHT){//左变色右不变色

            //绘制变色
            drawText(canvas,mChangePaint,0,middle)

            //绘制不变色
            drawText(canvas,mNormalPaint,middle,width)
        }else{
            drawText(canvas,mChangePaint,width-middle,width)

            //绘制不变色
            drawText(canvas,mNormalPaint,0,width-middle)
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

     private fun  drawText(canvas: Canvas?, paint: Paint, start:Int, end:Int){
        canvas?.save()
        val rect = Rect(start,0,end,height)
        //绘制不变色
        canvas?.clipRect(rect)
        val text = text.toString()
       val  bounds = Rect()
        paint.getTextBounds(text,0,text.length,bounds)//文字最小区域
        val x = width/2 - bounds.width()/2
        val fontMetrics = paint.fontMetricsInt
        val dy = (fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom
        val baseline = height/2 + dy
        canvas?.drawText(text,x.toFloat(),baseline.toFloat(),paint)
        canvas?.restore()
    }

    @Synchronized fun  setCurrentProgress(progress:Float){
        this.mCurrentProgress = progress
        invalidate()
    }

    @Synchronized fun setDirection(direction: Direction){
        this.mDirection = direction
    }


    enum class Direction{
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    @Synchronized fun setChangeColor(changeColor:Int){
        this.mChangePaint.color = changeColor
    }

    @Synchronized fun setOriginColor(originColor:Int){
        this.mNormalPaint.color = originColor
    }
}