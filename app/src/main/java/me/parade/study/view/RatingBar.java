package me.parade.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : parade
 * date : 2020/10/8
 * description :评分控件
 */
public class RatingBar extends View {

    private Bitmap mRatingNormal,mRatingSelected;
    private int mRatingNumber;

    public RatingBar(Context context) {
        this(context,null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        //此处不能使用vectorDrawable
        int normalId = array.getResourceId(R.styleable.RatingBar_rb_drawable_normal, R.drawable.star_normal);
        mRatingNormal = BitmapFactory.decodeResource(getResources(), normalId);
        int selectedId = array.getResourceId(R.styleable.RatingBar_rb_drawable_selected, R.drawable.star_selected);
        mRatingSelected = BitmapFactory.decodeResource(getResources(), selectedId);
        mRatingNumber = array.getInt(R.styleable.RatingBar_starNumber, 5);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         int width = mRatingNormal.getWidth()*mRatingNumber;
         int height = mRatingNormal.getHeight();

         setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mRatingNumber; i++) {
            int x = i*mRatingNormal.getWidth();
            canvas.drawBitmap(mRatingNormal,x,0,null);
        }

    }
}
