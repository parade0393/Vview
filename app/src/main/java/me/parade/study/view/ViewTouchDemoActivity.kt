package me.parade.study.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_view_touch_demo.*

private const val TAG = "TouchViewDemo"
class ViewTouchDemoActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_touch_demo)

        viewTouch.setOnTouchListener { v, event ->
            Log.d(TAG, "TouchListener: ${event.action}")
            tvNormal.append("TouchListener: ${event.action}\n")
            return@setOnTouchListener false
        }

        viewTouch.setOnClickListener {
            tvNormal.append("ClickListener->\n")
            Log.d(TAG, "ClickListener: ")
        }

        viewTouch.onPrintTouchActionListener = object :TouchViewDemo.OnPrintTouchActionListener{
            override fun printTouchEventCode(code: String) {
                tvNormal.append("onTouchEvent：$code\n")
            }

        }

        viewTouchTrue.setOnTouchListener{_,event ->
            tvTouchTrue.append("TouchListener: ${event.action}\n")
            return@setOnTouchListener false
        }

        viewTouchTrue.setOnClickListener {
            tvTouchTrue.append("ClickListener->\n")
        }

        viewTouchTrue.onPrintTouchActionListener = object :TouchViewTrueDemo.OnPrintTouchActionListener{
            override fun printTouchEventCode(code: String) {
                tvTouchTrue.append("onTouchEvent：$code\n")
            }

        }

        viewTouchListener.setOnTouchListener{_,event ->
            tvTouchListener.append("TouchListener: ${event.action}\n")
            return@setOnTouchListener true
        }

        viewTouchListener.setOnClickListener {
            tvTouchListener.append("ClickListener->\n")
        }

        viewTouchListener.onPrintTouchActionListener = object :TouchViewDemo.OnPrintTouchActionListener{
            override fun printTouchEventCode(code: String) {
                tvTouchListener.append("onTouchEvent：$code\n")
            }

        }
    }
}