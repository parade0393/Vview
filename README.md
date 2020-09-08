# Vview
自定义view的学习过程，希望可以坚持下来
### 构造函数何时使用
    只有一个参数(context) 在代码中使用时调用
    有两个参数(context,AttributeSet)在布局文件中使用时调用
    有三个参数(context,AttributeSet,defStyleAttr)在布局文件中使用且使用style时调用
### 测量模式
    MeasureSpec.AT_MOST 相当于布局中的wrap_content
    MeasureSpec.EXATLY 相当于布局中给的确切值或者match_parent
    MeasureSpec.UNSPECIFIED 一般很少用，在ScrollVie等滚动视图中使用
### 经常使用方法
    onMeasure 测量
    onDraw 绘制 默认Viegroup不会调用此方法
    onTouchEvent 事件处理
### onMeasure
    保存宽高 setMeasuredDimension(width,height) onMeasure最后一定要调这个方法，保存宽高
    获取宽高 
    Rect rect = new Rect()
    mPaint.getTextBounds(mText,0,mText.length,rect)
    width = rect.width()
