package me.parade.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import androidx.core.view.ViewCompat;

/**
 * @author : parade
 * date : 2020/10/21
 * description :滑动示例
 * HorizontalScrollView FrameLayout，所以只能由一个子view
 */
public class KGSlidingMenu extends HorizontalScrollView {

    private static final String TAG = "KGSlidingMenu";

    private int mMenuWidth;
   private View mContentView,menuView;
   private GestureDetector mGestureDetector;
   private boolean mMenuIsOpen;
   private boolean mIsIntercept;

    public KGSlidingMenu(Context context) {
        this(context,null);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KGSlidingMenu);
        int rightMargin = array.getDimensionPixelOffset(R.styleable.KGSlidingMenu_menuRightMargin,dp2px(50));
        mMenuWidth = getScreenWidth(context)-rightMargin;
        array.recycle();

        mGestureDetector = new GestureDetector(context, mGestureDetectorListener);
    }



    //1.宽度不对，需要指定宽度
    @Override
    protected void onFinishInflate() {
        //这个方法只是布局解析完毕，也就是xml布局文件解析完毕，在onCreate里执行的
        super.onFinishInflate();
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);

        menuView = viewGroup.getChildAt(0);
        ViewGroup.LayoutParams layoutParams = menuView.getLayoutParams();
        layoutParams.width = mMenuWidth;
        menuView.setLayoutParams(layoutParams);

        mContentView = viewGroup.getChildAt(1);
        ViewGroup.LayoutParams pa = mContentView.getLayoutParams();
        pa.width = getScreenWidth(getContext());

        mContentView.setLayoutParams(pa);

        //初始化进来时关闭的,
//        scrollTo(mMenuWidth,0);//发现没用
    }

    //在onResume之后进行的
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //2.初始化进来时关闭的
        scrollTo(mMenuWidth,0);
    }


    //4.处理内容区域缩放，菜单区域缩放和透明度


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (oldl>l){
//            Log.d(TAG, "向右: l->");
        }else {
//            Log.d(TAG, "向左: l->");
        }

        float scale = 1f*l/mMenuWidth;

        float rightSale = 0.7f+0.3f*scale;
        mContentView.setPivotX(0);
        mContentView.setPivotY(mContentView.getMeasuredHeight()/2);

        mContentView.setScaleX(rightSale);
        mContentView.setScaleY(rightSale);

        //菜单透明度从0.7-1
        float leftAlpha = 0.7f+(1-scale)*0.3f;
        menuView.setAlpha(leftAlpha);
        //缩放
        float leftScale = 0.7f+0.3f*(1-scale);
        menuView.setScaleY(leftScale);
        menuView.setScaleX(leftScale);
//        menuView.setTranslationX(l);//抽屉效果
        menuView.setTranslationX(0.25f*l);

    }

    //3.手指抬起时二选一，要么时打开，要么是闭合
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
       if ( mGestureDetector.onTouchEvent(ev)){
           //快速滑动，下面的就不要执行了
           return true;
       }

       if (mIsIntercept){
           //自己有拦截，下面的就不要执行了
           return true;
       }
        if (ev.getAction() == MotionEvent.ACTION_UP){
            //根据滑动距离判断
            if (getScrollX()> mMenuWidth/2){
                //关闭菜单
                closeMenu();
            }else {
                //打开菜单
                openMenu();
            }
            //确保super.onTouchEvent(ev)不会执行
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: "+ev.getAction());
        mIsIntercept = false;
            if (mMenuIsOpen) {
                if (ev.getX() > mMenuWidth) {
                    closeMenu();
                    mIsIntercept = true;
                    return true;//拦截子view的任何事件,但是会响应自己的onTouch事件
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(0,0);
        mMenuIsOpen = true;
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth,0);
        mMenuIsOpen = false;
    }

    private int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

    private int getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    private GestureDetector.OnGestureListener mGestureDetectorListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mMenuIsOpen){
                //菜单打开，快速向左滑动，关闭菜单
                if (velocityX<0) {
                    closeMenu();
                    return true;
                }
            }else {
                //菜单关闭，快速向右滑动，打开菜单
                if (velocityX>0) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };
}
