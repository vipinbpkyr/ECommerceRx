package com.example.ecommercedemo.ui.product


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.ecommercedemo.R
import kotlinx.android.synthetic.main.fragment_product_details.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView1.text = arguments?.getString("title")
        textView3.text = arguments?.getString("description")
        imageView2.showImage(arguments?.getString("image"))
//        Glide.with(this).load(arguments?.getString("image")).into(imageView)
    }


}

fun ImageView.showImage(url : String?) {
    Glide.with(context).load(url).into(this)
}
