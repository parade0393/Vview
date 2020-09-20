package me.parade.study.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author : parade
 * date : 2020/9/9
 * description :仿qq运动步数
 * 1.分析效果
 * 2.确定自定义属性，编写attrs.xml
 * 3.在布局中使用
 * 4.在自定义view中获取自定义属性
 * 5.onMeasure
 * 6.画外圆弧，内圆弧 文字
 * 7.其他
 */
class QQStepView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private var mOuterColor = Color.RED
    private var mInnerColor = Color.BLUE
    private var mBorderWidth = 20//20px
    private var mStepTextSize = 0
    private var mStepTextColor = 0


    private val mOuterPaint:Paint = Paint()
    private val mInnerPaint:Paint = Paint()
    private val mTextPaint:Paint = Paint()
    private val mRPaint = Paint()
    private val mAPaint = Paint()
    private var mStepMax:Int = 0
    private var mCurrentStep = 0

    init {
        attrs?.let {
            val array =
                context.obtainStyledAttributes(attrs, R.styleable.QQStepView)

            mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor)
            mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor)
            mBorderWidth =
                array.getDimensionPixelSize(R.styleable.QQStepView_borderWidth, mBorderWidth)
            mStepTextSize =
                array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize)
            mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)

            array.recycle()

            //初始化
            mOuterPaint.isAntiAlias = true
            mOuterPaint.strokeWidth = mBorderWidth.toFloat()
            mOuterPaint.color = mOuterColor
            mOuterPaint.style = Paint.Style.STROKE//画笔是空心
            mOuterPaint.strokeCap = Paint.Cap.ROUND

            mInnerPaint.isAntiAlias = true
            mInnerPaint.strokeWidth = mBorderWidth.toFloat()
            mInnerPaint.color = mInnerColor
            mInnerPaint.style = Paint.Style.STROKE//画笔是空心
            mInnerPaint.strokeCap = Paint.Cap.ROUND

            mTextPaint.isAntiAlias = true
            mTextPaint.color = mStepTextColor
            mTextPaint.textSize = mStepTextSize.toFloat()

            mRPaint.isAntiAlias = true
            mRPaint.strokeWidth = 1F
            mRPaint.color = Color.BLACK
            mRPaint.style = Paint.Style.STROKE

            mAPaint.isAntiAlias = true
            mAPaint.strokeWidth = 1F
            mAPaint.color = Color.RED
            mAPaint.style = Paint.Style.STROKE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //调用者在布局中可能使用wrap_content，则模式为 AT_MOST，我们给一个默认值40dp

        //宽度高度不一致 取最小值 保证是正方形
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)



        setMeasuredDimension(if (width>height)height else width,if (height>width) width else height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //6.1 画最外圆弧 ，遇见的问题，圆弧闭合了，是以为第3个参数应为false
        //边缘没显示完整：因为描边也有宽度

        //解决边缘没有显示完整
        val center = width/2
        val radius = width/2 - mBorderWidth/2//left = center-radius  right=center+radius
        val rectF = RectF((mBorderWidth/2).toFloat(),(mBorderWidth/2).toFloat(), (width-mBorderWidth/2).toFloat(),  (width-mBorderWidth/2).toFloat())
        //第3个参数为true的时候画显示空缺区域的边界半径以封闭圆弧(圆弧加半径)，fase则只画圆弧，不画半径
        canvas?.drawArc(rectF, 135F, 270F,false,mOuterPaint)

        val ourRc = Rect(0,0,width,height)
        canvas?.drawRect(ourRc,mRPaint)
        canvas?.drawRect(rectF,mAPaint)
        //6.2画内圆弧,进度是由使用者设置的
        if (mStepMax == 0) else{
            val sweepAngle = mCurrentStep/mStepMax.toFloat()
            canvas?.drawArc(rectF, 135F, 270F*sweepAngle,false,mInnerPaint)
        }

        //6.3画文字
        val stepText = "$mCurrentStep"
        val fontMetrics = mTextPaint.fontMetrics
        val textBounds = Rect()
        mTextPaint.getTextBounds(stepText,0,stepText.length,textBounds)
        canvas?.drawText(stepText,(width/2-textBounds.width()/2).toFloat(),width/2+(fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom,mTextPaint)
    }

    @Synchronized fun  setStepMax(stepMax:Int){
        this.mStepMax = stepMax
    }

    @Synchronized fun setCurrentStep(currentStep:Int){
        this.mCurrentStep = currentStep
        invalidate()
    }

}