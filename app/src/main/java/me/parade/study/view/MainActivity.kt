package me.parade.study.view

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
    }

}