package me.parade.study.view

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
private const val TAG = "parade0393"
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private  var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* Log.d(TAG, "onCreate: ${btnStartStep.measuredHeight}")//0
        btnStartStep.post {
            Log.d(TAG, "post: ${btnStartStep.measuredHeight}")//126
        }*/

        initEvent()
    }

    private fun initEvent() {
        btnLeftToRight.setOnClickListener(this)
        btnRightToLeft.setOnClickListener(this)
        btnToVP.setOnClickListener(this)
        btnStartStep.setOnClickListener(this)
        btnProgress.setOnClickListener(this)
        btnShapeChange.setOnClickListener(this)
        btnCancelShapeChange.setOnClickListener(this)
        btnTime.setOnClickListener(this)
        btnTest.setOnClickListener(this)
        btnSliding.setOnClickListener(this)
        btnCombineText.setOnClickListener(this)
        btnQQ.setOnClickListener(this)
        btnVertical.setOnClickListener(this)
        btnViewTouch.setOnClickListener(this)
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
           R.id.btnStartStep->{
               stepView.setStepMax(etMax.text.toString().trim().toInt())
               val animator = ObjectAnimator.ofInt(0,etCurrent.text.toString().trim().toInt())
               animator.duration = 1000
               animator.interpolator = DecelerateInterpolator()
               animator.addUpdateListener {
                   stepView.setCurrentStep(it.animatedValue as Int)
               }
               animator.start()
           }
           R.id.btnProgress -> {
               val animator = ObjectAnimator.ofFloat(0f, etProgressCurrent.text.toString().toFloat())
               animator.duration = 2000
               animator.interpolator = DecelerateInterpolator()
               animator.addUpdateListener {
                   val animatedValue = (it.animatedValue as Float).toInt()
                   progress.updateProgress(animatedValue)
               }
               animator.start()
           }
           R.id.btnShapeChange -> {
               //定时器的最简单的一种实现方式
               timer = Timer()
               timer?.schedule(object :TimerTask(){
                 override fun run() {
                     runOnUiThread {
                         shapeView.exchange()
                     }
                 }

             },1000,1000)
           }
           R.id.btnCancelShapeChange->{
               //取消定时器
               timer?.cancel()
           }

           R.id.btnTime -> {
               val instance = Calendar.getInstance()
               DatePickerDialog(
                   this,
                   0,
                   { _, year, month, dayOfMonth ->
                       Toast.makeText(this, "year:$year,month:$month,day:$dayOfMonth", Toast.LENGTH_SHORT).show()
                   },
                   instance.get(Calendar.YEAR),
                   instance.get(Calendar.MONTH),
                   instance.get(Calendar.DAY_OF_MONTH)
               ).show()
           }
           R.id.btnTest->{
               startActivity(Intent(this,TestActivity::class.java))
           }
           R.id.btnSliding->{
               startActivity(Intent(this,SlidingMenuCaseActivity::class.java))
           }
           R.id.btnCombineText->{
            startActivity(Intent(this,TextViewActivity::class.java))
           }
           R.id.btnQQ->{
               startActivity(Intent(this,QQCaseActivity::class.java))
           }
           R.id.btnVertical->{
               startActivity(Intent(this,VerticalDragDemoActivity::class.java))
           }
           R.id.btnViewTouch->{
               startActivity(Intent(this,ViewTouchDemoActivity::class.java))
           }
       }
    }

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

    override fun onResume() {
//        Log.d(TAG, "onResume before: ${btnStartStep.measuredHeight}")//0
        super.onResume()
//        Log.d(TAG, "onResume after: ${btnStartStep.measuredHeight}")//0
    }
}