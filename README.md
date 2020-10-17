# Vview

自定义view的学习过程，希望可以坚持下来,学习 1.ActivityThread,Binder，WindowManager

#### 构造函数何时使用

    只有一个参数(context) 在代码中使用时调用
    有两个参数(context,AttributeSet)在布局文件中使用时调用
    有三个参数(context,AttributeSet,defStyleAttr)在布局文件中使用且使用style时调用

#### 测量模式

    MeasureSpec.AT_MOST 相当于布局中的wrap_content
    MeasureSpec.EXATLY 相当于布局中给的确切值或者match_parent
    MeasureSpec.UNSPECIFIED 一般很少用，在ScrollVie等滚动视图中使用

#### 经常使用方法

    onMeasure 测量
    onDraw 绘制 默认Viegroup不会调用此方法
    onTouchEvent 事件处理

#### onMeasure

    保存宽高 setMeasuredDimension(width,height) onMeasure最后一定要调这个方法，保存宽高

#### 为什么不能在子线程中更新UI

​		开了线程，更新UI时一般会调用setText(),setImageResouce()等等方法肯定会调用invalidate，而invalidate最终会调用checkThread()方法

```java
 void checkThread() {
     // mThread = Thread.currentThread();构造函数中初始化
     if (mThread != Thread.currentThread()) {
         throw new CalledFromWrongThreadException(
                 "Only the original thread that created a view hierarchy can touch its views.");
     }
 }
```

performTraversals非常重要的方法，invalidate会调用这个方法，这个方法又会调用performMeasure(),performLayout()和performDraw()

invalidate 会一路网上跑，跑到最上层，然后再往下画,它会牵连着整个layout布局中的view

#### view的绘制流程
ViewRoot对应于ViewRootImpl类，它是连接WindowManager和DecorView的纽带，View的三大流程均是通过ViewRoot来完成的
```kotlin
//在onCreate里
Log.d(TAG, "onCreate: ${btnStartStep.measuredHeight}")//0
btnStartStep.post {
    Log.d(TAG, "post: ${btnStartStep.measuredHeight}")//126 也是最后输出
}
//在onResume里
override fun onResume() {
    Log.d(TAG, "onResume before: ${btnStartStep.measuredHeight}")//0
    super.onResume()
    Log.d(TAG, "onResume after: ${btnStartStep.measuredHeight}")//0
}
```
setContentView()只是new DecorView()然后把我们的布局添加进去，并没有测量，所以再onCreate里为0







#### view的绘制流程
源头：绘制->` wm.addView(decor, l);`->`WindowManagerImpl.addView()`
   -> `root.setView(view, wparams, panelParentView);`->`requestLayout()`->`scheduleTraversals(),mTraversalRunnable`->`doTraversal()`->`performTraversals()->performMeasure（measure）->performLayout(layout)`
   测量的时候先从最外层往里走，父View的测量模式传递给子view，最后子view测量完宽高之后，再往回走，计算父View的宽高
第一步：performMeasure：用于指定测量layout中所有控件的宽高，对于ViewGroup，先去测量里面的子孩子，根据子孩子的宽高再来计算自己的宽高，对于View，它的宽高是由自己和父布局的测量模式决定的
第二步：performLayout:用于摆放子布局，for循环所有子view，用child.layout来摆放子view，
第三步：用于绘制自己还有子view，绘制背等
问题：
1.想要获取view的宽高，前提肯定要调用测量方法，测量完毕之后才能获取宽高
2.view的绘制流程是在onResume之后开始(activity启动流程)
3.addView,setVisibility等会重新调用requestLayout(),重新走一遍view的绘制