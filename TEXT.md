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

`getDimension`--获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘   返回float
`getDimensionPixelOffset`--获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘  返回int
`getDimensionPixelSize`--则不管写的是dp还是sp还是px,都会乘以density

尺寸：
`density`--手机密度：以160dpi为准。即dpi=160时Density为1，，Android 规定在 160 dpi 的设备上 1dp = 1px，当屏幕密度为320dpi时1dp=2px即density为2
            可以看出px=dp*density,而density可用代码求得`context.getResources().getDisplayMetrics().density`
`dpi`--手机像素密度:用屏幕对角线的像素数/对角线的长(通常用的屏幕尺寸)，，，在电子屏幕显示中提到的 ppi 和 dpi 可以认为是一样的，所以你可以忽略在措辞上用 dpi 或者 ppi 有什么不同，不过在 Android 平台上常用 dpi 这种表述方式
所以网上用的dp和px转换的工具
```kotlin
private fun dp2px(context:Context,dp:Int):Int{
    val density = context.resources.displayMetrics.density
    return (dp*density+0.5f).toInt()//加0.5是为了在精度丢失的情况下进行四舍五入
}
private fun px2dp(context: Context,px:Float):Int{
    val scale = context.resources.displayMetrics.density
    return (px/scale+0.5f).toInt()
}
private fun sp2px(context: Context,spValue:Float):Int{
    //从代码中也可以看出 sp 和 px 间转换使用的是 scaledDensity，而 dp 和 px 间转换使用的是 density，也就是 sp 会随着系统字体设置缩放，dp 不会
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue*fontScale+0.5f).toInt()
}
private fun px2sp(context: Context,pxValue:Float):Int{
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (pxValue/fontScale+0.5f).toInt()
}
```
                                 