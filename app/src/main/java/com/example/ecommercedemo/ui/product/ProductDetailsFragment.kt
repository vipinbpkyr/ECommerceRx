package com.example.ecommercedemo.ui.product


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ecommercedemo.R
import com.example.ecommercedemo.di.Injectable
import com.example.ecommercedemo.vo.Cart
import kotlinx.android.synthetic.main.fragment_product_details.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductDetailsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            productViewModel = ViewModelProviders.of(it, viewModelFactory)
                .get(ProductViewModel::class.java)
        }

        button.setOnClickListener { if (button.text.toString().equals("Add to Cart")) {
            productViewModel.addToCart()
        }else{
            productViewModel.removeCart()

        }
        }
        textView1.text = arguments?.getString("title")
        textView3.text = arguments?.getString("description")
        imageView2.showImage(arguments?.getString("image"))
//        Glide.with(this).load(arguments?.getString("image")).into(imageView)

        productViewModel.observeCartCountById().observe(this, Observer { result ->
        if(result > 0) button.text = "Remove from Cart"
        else button.text = "Add to Cart"})
    }

}

fun ImageView.showImage(url : String?) {
    Glide.with(context).load(url).into(this)
}
