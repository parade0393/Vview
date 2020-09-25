package me.parade.study.view

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_view_pager.*
import java.lang.StringBuilder

class ViewPagerActivity : AppCompatActivity() {

    private val titleList = listOf<String>("直播","推荐","视频","图片","段子","精华")
    private val mIndicators = mutableListOf<ColorTrackTextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        initIndicator()

        initViewpager()
    }

    private fun initIndicator() {
        titleList.forEach {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 1F
            val colorTrackTextView = ColorTrackTextView(this)
            colorTrackTextView.gravity = Gravity.CENTER_HORIZONTAL
            colorTrackTextView.layoutParams = layoutParams
            colorTrackTextView.setChangeColor(resources.getColor(R.color.colorAccent))
            colorTrackTextView.text = it

            llContainer.addView(colorTrackTextView)

        }
    }

    private fun initViewpager() {
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount(): Int {
               return titleList.size
            }

            override fun getItem(position: Int): Fragment {
                return ItemFragment.newInstance(titleList[position])
            }

        }
    }
}