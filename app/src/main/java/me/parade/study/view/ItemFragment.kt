package me.parade.study.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item.*

/**
 * @author : parade
 * date : 2020/9/25
 * description :
 */
class ItemFragment:Fragment() {

    companion object{
        @JvmStatic
        fun newInstance(title:String) = ItemFragment().apply {
            arguments = Bundle().apply {
                putString("title",title)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("title")?.let {
            textView.text = it
        }
    }
}