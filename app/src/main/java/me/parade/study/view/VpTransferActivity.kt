package me.parade.study.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_vp_transfer.*
import kotlin.math.abs

class VpTransferActivity : AppCompatActivity() {

    private var views = mutableListOf<View>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vp_transfer)

        val left2 = TextView(this).also {
            it.textSize = 23f
            it.gravity = Gravity.CENTER
            it.text = "左二"
            it.setBackgroundColor(Color.parseColor("#33b5e5"))
            views.add(it)
        }
        val left1 = TextView(this).also {
            it.textSize = 23f
            it.gravity = Gravity.CENTER
            it.text = "左一"
            it.setBackgroundColor(Color.parseColor("#99cc00"))
            views.add(it)
        }

        val middle = TextView(this).also {
            it.textSize = 23f
            it.gravity = Gravity.CENTER
            it.text = "中间"
            it.setBackgroundColor(Color.parseColor("#cc0000"))
            views.add(it)
        }

        val right1 = TextView(this).also {
            it.textSize = 23f
            it.gravity = Gravity.CENTER
            it.text = "右一"
            it.setBackgroundColor(Color.parseColor("#aa66cc"))
            views.add(it)
        }

        val right2 = TextView(this).also {
            it.textSize = 23f
            it.gravity = Gravity.CENTER
            it.text = "右二"
            it.setBackgroundColor(Color.parseColor("#ffbb33"))
            views.add(it)
        }

        viewPager.pageMargin = 20
        val minScale = 0.8f
        viewPager.setPageTransformer(false, object : ViewPager.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                when {
                    position<-1.0f -> {
                        page.scaleY = 0.8f
                    }
                    position<=0.0f -> {
                        page.scaleY = 1 + position - 0.8f * position

                    }
                    position<=1.0f -> {
                        page.scaleY = 1 - position + 0.8f * position
                    }
                    else -> {
                        page.scaleY = 0.8f
                    }
                }

            }

        })

        viewPager.adapter = object :PagerAdapter(){
            override fun getCount(): Int {
                return views.size
            }



            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(views[position])
                return views[position]
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(views[position])
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

        }

    }



}