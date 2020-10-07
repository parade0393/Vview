package me.parade.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : parade
 * date : 2020/10/2
 * description :
 */
public class ShapeView extends View {

    private Shape shape = Shape.Circle;
    private Paint mPaint;
    private Path path;

    public ShapeView(Context context) {
        this(context,null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (shape) {
            case Circle:
                mPaint.setColor(Color.RED);
                int center = getWidth() / 2;
                canvas.drawCircle(center,center,center,mPaint);
                break;
            case Square:
                mPaint.setColor(Color.YELLOW);
                canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
                break;
            case Triangle:
                mPaint.setColor(Color.BLUE);
                if (path == null){
                    path = new Path();
                    //开始先用moveTo
                    path.moveTo((float) getWidth()/2,0);
                    path.lineTo(0, (float) (getWidth()/2*(Math.sqrt(3f))));
                    path.lineTo(getWidth(),(float) (getWidth()/2*(Math.sqrt(3f))));
                    path.close();
                }

                canvas.drawPath(path,mPaint);
                break;
        }
    }



    enum Shape{
        Circle,
        Square,
        Triangle
    }

    public void exchange(){
        switch (shape) {
            case Circle:
                shape = Shape.Square;
                break;
            case Square:
                shape = Shape.Triangle;
                break;
            case Triangle:
                shape = Shape.Circle;
                break;
        }
        invalidate();
    }
}
