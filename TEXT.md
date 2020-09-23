FontMetrics:
FontMetrics 中的top，bottom，accent，descent代表变量值(和baseline之间的差值)，换算公式如下：
            FontMetrics.top = top线的y坐标-baseline线的y坐标
            FontMetrics.descent = descent线的y坐标-baseline线的y坐标...
获取FontMetrics `paint.getFontMetrics()`
字符串区域所占高度：`int height = FontMetrics.bottom-FontMetrics.top`,字符串区域所占宽度:`int width = paint.measureText("text")`
字符串最小矩形：
```kotlin
Rect rect = Rect()
paint.getTextBounds("text",0,text.length,rect)//rect即为最小矩形
```

关于文字宽高的几种算法，计算绘制文字时的x时一般用textBounds
```kotlin
val text = "parade0393"
val paint = Paint()
paint.textSize = 120F
val fontMetrics = paint.fontMetrics
Log.d("TLog", "fontMetrics,height,max: ${fontMetrics.bottom - fontMetrics.top}")//159.25781
Log.d("TLog", "fontMetrics,height,small: ${fontMetrics.descent - fontMetrics.ascent}")//140.625
val measureText = paint.measureText(text)
Log.d("TLog", "measureText,width: $measureText")//636
val rect = Rect()
paint.getTextBounds(text, 0, text.length, rect)
Log.d("TLog", "getTextBounds,width: ${rect.right-rect.left}")//621
Log.d("TLog", "getTextBounds,height: ${rect.bottom - rect.top}")//115
Log.d("TLog", "getTextBounds: ${rect.toShortString()}")//[8,-90][629,25]
```