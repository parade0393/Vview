package me.parade.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * @author : parade
 * date : 2020/10/25
 * description :
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        this(context,null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getCount() == 0){
            return false;
        }else if (getChildAt(0).getTop() == 0){
            return false;
        }else {
            return super.onInterceptTouchEvent(ev);
        }
    }
}
