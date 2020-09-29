package me.parade.study.view

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*stepView.setStepMax(4000)

        val animator = ObjectAnimator.ofFloat(0F, 3000F)
        animator.duration = 1000
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            val animatedValue = it.animatedValue as Float

            stepView.setCurrentStep(animatedValue.toInt())
        }

        animator.start()*/
        button.setOnClickListener {
            colorView.setDirection(ColorTrackTextViewJava.Direction.LEFT_TO_RIGHT)
            val animator = ObjectAnimator.ofFloat(0F, 1F)
            animator.duration = 2000L
            animator.addUpdateListener {
                val animatedValue = ( it.animatedValue) as Float
                colorView.setCurrentProgress(animatedValue)
            }
            animator.start()
        }

        button2.setOnClickListener {
            colorView.setDirection(ColorTrackTextViewJava.Direction.RIGHT_TO_LEFT)
            val animator = ObjectAnimator.ofFloat(0F, 1F)
            animator.duration = 2000L
            animator.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                colorView.setCurrentProgress(animatedValue)
            }
            animator.start()
        }
    }

}