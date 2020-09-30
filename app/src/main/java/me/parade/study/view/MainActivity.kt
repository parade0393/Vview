package me.parade.study.view

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartStep.setOnClickListener {
            stepView.setStepMax(etMax.text.toString().trim().toInt())
            val animator = ObjectAnimator.ofInt(0,etCurrent.text.toString().trim().toInt())
            animator.duration = 1000
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener {
                stepView.setCurrentStep(it.animatedValue as Int)
            }
            animator.start()
        }

        initEvent()
    }

    private fun initEvent() {
        btnLeftToRight.setOnClickListener(this)
        btnRightToLeft.setOnClickListener(this)
        btnToVP.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.btnLeftToRight->{
               colorView.setDirection(ColorTrackTextViewJava.Direction.LEFT_TO_RIGHT)
               val animator = ObjectAnimator.ofFloat(0F, 1F)
               animator.duration = 2000
               animator.interpolator = DecelerateInterpolator()
               animator.addUpdateListener {
                    colorView.setCurrentProgress(it.animatedValue as Float)
               }
               animator.start()
           }
           R.id.btnRightToLeft->{
               colorView.setDirection(ColorTrackTextViewJava.Direction.RIGHT_TO_LEFT)
               val animator = ObjectAnimator.ofFloat(0f, 1f)
               animator.duration = 1000
               animator.interpolator = AccelerateInterpolator()
               animator.addUpdateListener {
                   colorView.setCurrentProgress(it.animatedValue as Float)
               }
               animator.start()
           }
           R.id.btnToVP-> startActivity(Intent(this,ViewPagerActivity::class.java))
       }
    }

}