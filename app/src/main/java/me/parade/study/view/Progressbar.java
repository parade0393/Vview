package me.parade.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : parade
 * date : 2020/9/30
 * description : 进度条
 */
public class Progressbar extends View {

    private int mBoardWidth;
    private int mNormalColor;
    private int mProgressColor;
    private int mProgressTextSize;
    private int mProgressTextColor;

    private Paint normalPaint,progressPaint,textPaint;
    private float mCurrentProgress = 0f;

    private RectF progressRect = new RectF();
    private Rect textBound = new Rect();

    public Progressbar(Context context) {
        this(context,null);
    }

    public Progressbar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public Progressbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context,attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Progressbar);
        mBoardWidth = typedArray.getDimensionPixelSize(R.styleable.Progressbar_circleProgressBorderWidth, dp2px(context, 2));
        mNormalColor = typedArray.getColor(R.styleable.Progressbar_circleProgressNormalColor, Color.GRAY);
        mProgressColor = typedArray.getColor(R.styleable.Progressbar_circleProgressbarColor, Color.RED);
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.Progressbar_circleProgressTextSize, sp2px(context, 16));
        mProgressTextColor = typedArray.getColor(R.styleable.Progressbar_circleProgressTextColor, Color.GRAY);

        typedArray.recycle();

        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setColor(mNormalColor);
        normalPaint.setStrokeWidth(mBoardWidth);
        normalPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(mProgressColor);
        progressPaint.setStrokeWidth(mBoardWidth);
        progressPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(mProgressTextSize);
        textPaint.setColor(mProgressTextColor);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆
        canvas.drawCircle((float) getWidth()/2,(float)getWidth()/2,(float) (getWidth()/2-mBoardWidth/2),normalPaint);


        //画进度弧
        progressRect.set(mBoardWidth/2,mBoardWidth/2,getWidth()-mBoardWidth/2,getHeight()-mBoardWidth/2);
        canvas.drawArc(progressRect,0f,mCurrentProgress/100*360,false,progressPaint);


        //画文字
       String content = ((int)(mCurrentProgress)) +"%";
       textPaint.getTextBounds(content,0,content.length(),textBound);
        int dx = getWidth()/2-textBound.width()/2;
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = getHeight()/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(content,dx,dy,textPaint);
    }

    private int dp2px(Context context, int dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*density+0.5F);
    }

    private int sp2px(Context context,int spValue){
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*scaledDensity+0.5f);
    }

    public synchronized void updateProgress(int percent){
        if (percent>100){
            percent = 100;
        }else if (percent<0){
            percent = 0;
        }
        this.mCurrentProgress = percent;
        invalidate();
    }
}
