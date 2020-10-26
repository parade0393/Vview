package me.parade.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ListViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * @author : parade
 * date : 2020/10/25
 * description :
 * 可滑动的视图用列表，默认只能滑动listview
 */
public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper dragHelper;
    private View listView,menuView;
    private int menuHeight;
    private boolean mMenuIsOpen = false;

    public VerticalDragListView(@NonNull Context context) {
        this(context,null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //2.创建ViewDragHelper实例，其中需要callBack
        dragHelper = ViewDragHelper.create(this, callback);
    }

    //1.此处没有做验证，必须有两个子view
    //第一个子view是在上面的子view。不需要滑动
    //第二个子view是滑动的列表
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();//xml加载完毕
        menuView = getChildAt(0);
        listView = getChildAt(1);
    }

    //3事件处理交给ViewDragHelper
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }


    float mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mMenuIsOpen){
            //菜单打开的时候全部拦截
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                dragHelper.processTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float downY = event.getY();
                if (downY>mDownY && !canChildScrollUp()){
                    //向下滑动，且不可以向下滚动
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    /**
     * 判断是否可以向下滚动
     * @return true:可以向下滚动，false：不可向下滚动
     */
    public boolean canChildScrollUp() {
        if (listView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) listView, -1);
        }
        return listView.canScrollVertically(-1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            menuHeight = menuView.getMeasuredHeight();
        }
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            //只有listView才能滑动
            return child == listView;
        }

        //这里只能垂直拖动，需要水平拖动，则重写另外一个方法
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            //滑动超过菜单view的高度，则不能滑动(向下滑动不能超过menuView)
            if (top>=menuHeight){
                top = menuHeight;
            }
            if (top<=0){
                //向上滑动不能超过父view
                top = 0;
            }
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {

                if (listView.getTop() > menuHeight / 2) {
                    //打开
                    dragHelper.settleCapturedViewAt(0, menuHeight);
                    mMenuIsOpen = true;
                } else {
                    //关闭
                    dragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }

    };


    //listView可以滑动，但是菜单没有效果了

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)){
            invalidate();
        }
    }
}
