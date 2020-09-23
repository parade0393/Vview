package me.parade.study.view

import android.animation.ObjectAnimator
import android.graphics.Paint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
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
            colorView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
            val animator = ObjectAnimator.ofFloat(0F, 1F)
            animator.duration = 2000L
            animator.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                colorView.setProgress(animatedValue)
            }
            animator.start()
        }

        button2.setOnClickListener {
            colorView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
            val animator = ObjectAnimator.ofFloat(0F, 1F)
            animator.duration = 2000L
            animator.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                colorView.setProgress(animatedValue)
            }
            animator.start()
        }
    }
}