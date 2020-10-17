package me.parade.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : parade
 * date : 2020/10/16
 * description :简易流式布局
 */
public class MyTagLayout extends ViewGroup {
    private static final String TAG = "MyTagLayout";
    //总的视图集合
    private List<List<View>> mChildViews = new ArrayList<>();
    //每一行的视图集合
    List<View> lineChildViews = new ArrayList<View>();

    int mIntervalHorizontal,mInternalVertical;

    public MyTagLayout(Context context) {
        this(context,null);
    }

    public MyTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTagLayout);
        mIntervalHorizontal = a.getDimensionPixelOffset(R.styleable.MyTagLayout_tag_interval_between_horizontal, dp2px(10));
        mInternalVertical = a.getDimensionPixelOffset(R.styleable.MyTagLayout_tag_interval_between_vertical, dp2px(10));
        a.recycle();
    }

    private int dp2px(int dValue) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dValue*density+0.5);
    }


    /**
     * 测量，主要是计算高
     * width和height是包括padding的
     * getMeasuredWidth也包括padding
     * 控件的margin是不会合并的A控件的右外边距加B控件的左外边距等于两个控件间的间距
     * ViewGroup默认是支持margin的
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //清空集合,这里一定要先清空集合
        mChildViews.clear();
        lineChildViews.clear();
        int childCount = getChildCount();

        //获取到宽度,不用计算外边距和内边距
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //初始高度，没有计算子view时的高度，需要加上padding
        int height = getPaddingTop() + getPaddingBottom();//高度是包括padding的
//        int height = MeasureSpec.getSize(heightMeasureSpec);//这里不这样计算，就是达到了wrap_content的效果，因为如果getSize()默认不支持wrap_content，默认是父布局剩余高度

        //一行的宽度
        int lineWidth = getPaddingLeft()+getPaddingRight();
        mChildViews.add(lineChildViews);//先把第一行的view集合添加到总集合中

        //每一行上一个view的高度
        int lastViewHeight= 0;


        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //测量子view，必须执行这个才能得到子view的宽高
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            // margin值 ViewGroup.LayoutParams 没有 就用系统的MarginLayoutParams
            // 想想 LinearLayout为什么有？
            // LinearLayout有自己的 LayoutParams  会复写一个非常重要的方法
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams)childView.getLayoutParams();

            //一行不够的情况下，考虑换行,考虑最后一个的右边距
            if (lineWidth+childView.getMeasuredWidth()+ mIntervalHorizontal *2+layoutParams.leftMargin+layoutParams.rightMargin>width){
                //换行，记住view的所占的高度
                lastViewHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                //换行累加高度,累加竖直间距
                height += childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin+mInternalVertical;

                //更新linewidth,这里是=
                lineWidth = childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;

                lineChildViews = new ArrayList<View>();//换行重写new一个集合
                mChildViews.add(lineChildViews);
            }else {

                int currentViewHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                //如果后来view比上一个view高，则更新高度
                // 则先减去上一个view的高度再加，避免重复添加
                if (currentViewHeight>lastViewHeight){
                    height = height - lastViewHeight + currentViewHeight;
                }
                //第一行并且是第一个view，加上竖直间距
                if (lastViewHeight==0 && i==0){
                    height += mInternalVertical;
                }
                //更新lastViewHeight
                lastViewHeight = currentViewHeight;
                //不换行，累计行宽，这里是+= 加上间距，一个控件一个mInterval(左边距)
                lineWidth+=childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin+ mIntervalHorizontal;
            }

            lineChildViews.add(childView);

        }

        setMeasuredDimension(width,height);
    }

    /**
     *摆放子view,这里考虑了每一个子view的内边距和外边距
     * 摆放的时候不应该考虑padding
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left, top = getPaddingTop(), right, bottom;

        for (int i = 0; i < mChildViews.size(); i++) {

        }
        int lineMaxHeight=0;
//        for (List<View> mChildView : mChildViews) {
        for (int i = 0; i < mChildViews.size(); i++) {
            List<View> mChildView = mChildViews.get(i);
            left = getPaddingLeft();
            lineMaxHeight = 0;
            for (View view : mChildView) {
                ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                left+=layoutParams.leftMargin+ mIntervalHorizontal;
                int childTop = top  + layoutParams.topMargin+mInternalVertical;
                right = left+view.getMeasuredWidth();
                bottom = childTop  + view.getMeasuredHeight();

                view.layout(left,childTop,right,bottom);//到这里getMeasureWidth和getWidth值是一样的，但是我如果给right+10,那么getWidth也会加10，getMeasureWidth不会变
                lineMaxHeight = Math.max(lineMaxHeight,  view.getMeasuredHeight()+layoutParams.bottomMargin);
                left += view.getMeasuredWidth() + layoutParams.rightMargin;
            }
            top+=lineMaxHeight+mInternalVertical;
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }



}
