package com.example.ecommercedemo.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.ecommercedemo.AppExecutors
import com.example.ecommercedemo.R
import com.example.ecommercedemo.ui.common.DataBoundListAdapter
import com.example.ecommercedemo.databinding.ProductItemBinding
import com.example.ecommercedemo.vo.Product

class ProductListAdapter(private val dataBindingComponent: DataBindingComponent,
                         appExecutors: AppExecutors, private val clickCallback: ((Product) -> Unit)?) : DataBoundListAdapter<Product, ProductItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ProductItemBinding {
        val binding = DataBindingUtil.inflate<ProductItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.product_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.product?.let {
                clickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ProductItemBinding, item: Product) {
        binding.product = item
    }
}