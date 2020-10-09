package me.parade.study.view;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : parade
 * date : 2020/10/10
 * description :
 */
public class LetterIndexBar extends View {

    private Paint normalPaint,selectedPaint;
    private int mNormalTextColor,mSelectedTextColor;
    private int mTextSize;
    private int currentSelectPosition = -1;

    private static final String[] INDEXES = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int itemHeight;

    public LetterIndexBar(Context context) {
        this(context,null);
    }

    public LetterIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterIndexBar);
        mNormalTextColor = array.getColor(R.styleable.LetterIndexBar_letterTextColor, 0xFF616161);
        mSelectedTextColor = array.getColor(R.styleable.LetterIndexBar_letterSelectedColor, 0xFF616161);
        mTextSize = array.getDimensionPixelOffset(R.styleable.LetterIndexBar_letterTextSize, sp2px(12));

        array.recycle();

        normalPaint = new Paint();
        normalPaint.setTextSize(mTextSize);
        normalPaint.setColor(mNormalTextColor);
        normalPaint.setAntiAlias(true);

        selectedPaint = new Paint();
        selectedPaint.setTextSize(mTextSize);
        selectedPaint.setColor(mSelectedTextColor);
        selectedPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = (int) (normalPaint.measureText("A") + getPaddingLeft() + getPaddingRight());
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (itemHeight == 0){
            itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/INDEXES.length;
        }
        for (int i = 0; i < INDEXES.length; i++) {
            int x = (int) (getWidth() / 2 - normalPaint.measureText(INDEXES[i]) / 2);
            int letterCenterT = i * itemHeight + itemHeight/2;
            Paint.FontMetricsInt fontMetricsInt = normalPaint.getFontMetricsInt();
            int baseline = letterCenterT+(fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
            if (currentSelectPosition == i){
                canvas.drawText(INDEXES[i],x,baseline,selectedPaint);
            }else {
                canvas.drawText(INDEXES[i],x,baseline,normalPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float distance = event.getY();
                int position = (int) (distance / itemHeight);
                if (position == currentSelectPosition)return true;
                if (position<0) position = 0;
                if (position>INDEXES.length-1) position = INDEXES.length - 1;
                currentSelectPosition = position;
                if (letterTouchListener != null && currentSelectPosition>=0){
                    letterTouchListener.letterChange(INDEXES[currentSelectPosition],currentSelectPosition,false);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (letterTouchListener != null && currentSelectPosition>=0){
                    letterTouchListener.letterChange(INDEXES[currentSelectPosition],currentSelectPosition,true);
                }
                currentSelectPosition = -1;
                invalidate();
                break;
        }
        return true;
    }

    private int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,getResources().getDisplayMetrics());
    }

    private LetterTouchListener letterTouchListener;

    public void setLetterTouchListener(LetterTouchListener letterTouchListener){
        this.letterTouchListener = letterTouchListener;
    }

    interface LetterTouchListener{
        void letterChange(String letter,int position,boolean isTouch);
    }
}
