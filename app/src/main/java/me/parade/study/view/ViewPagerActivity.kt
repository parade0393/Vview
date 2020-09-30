package me.parade.study.view

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_view_pager.*
import java.lang.Exception

class ViewPagerActivity : AppCompatActivity() {

    private val titleList = arrayOf("直播","推荐","视频","图片","段子","精华")
    private val mIndicators = mutableListOf<ColorTrackTextViewJava>()

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
            val colorTrackTextView = ColorTrackTextViewJava(this)
            colorTrackTextView.layoutParams = layoutParams
            colorTrackTextView.setChangeColor(resources.getColor(R.color.colorAccent))
            colorTrackTextView.text = it
            llContainer.addView(colorTrackTextView)
            mIndicators.add(colorTrackTextView)

        }
    }

    private fun initViewpager() {

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
               return titleList.size
            }

            override fun getItem(position: Int): Fragment {
                return ItemFragment.newInstance(titleList[position])
            }

        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //页面初始化的时候也会调用一次
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val left = mIndicators[position]
                left.setDirection(ColorTrackTextViewJava.Direction.RIGHT_TO_LEFT)
                left.setCurrentProgress(1-positionOffset)

                try {
                    val right = mIndicators[position + 1]
                    right.setDirection(ColorTrackTextViewJava.Direction.LEFT_TO_RIGHT)
                    right.setCurrentProgress(positionOffset)
                }catch (e:Exception){}
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }
}