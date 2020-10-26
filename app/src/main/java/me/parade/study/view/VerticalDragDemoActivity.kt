package me.parade.study.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_vertical_drag_demo.*

class VerticalDragDemoActivity : AppCompatActivity() {

    private val mItems by lazy { mutableListOf<String>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_drag_demo)

        for (index in 0..200){
            mItems.add("index ->$index")
        }

        lv_view.adapter = object :BaseAdapter(){
            override fun getCount(): Int {
                return mItems.size
            }

            override fun getItem(position: Int): Any? {
                return null;
            }

            override fun getItemId(position: Int): Long {
                return 0L
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val tv = LayoutInflater.from(this@VerticalDragDemoActivity)
                    .inflate(R.layout.item_lilst, parent, false) as TextView
                tv.text = mItems.get(position)
                return tv
            }

        }
    }
}