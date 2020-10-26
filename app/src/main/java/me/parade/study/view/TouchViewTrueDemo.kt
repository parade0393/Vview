package me.parade.study.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * @author : parade
 * date : 2020/10/26
 * description :ViewTouchDemo
 */
private const val TAG = "TouchViewDemo"
class TouchViewTrueDemo:View {
    constructor(context: Context?) : super(context){
        init(null,0)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(attrs,0)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(attrs,defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        context
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(TAG, "onTouchEvent: ${event?.action}")
        onPrintTouchActionListener?.printTouchEventCode("${event?.action}")
        return true
    }

     var onPrintTouchActionListener:OnPrintTouchActionListener? = null


    interface OnPrintTouchActionListener{
        fun printTouchEventCode(code:String)
    }

}