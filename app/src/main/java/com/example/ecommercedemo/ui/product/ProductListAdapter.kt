package com.example.ecommercedemo.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.ecommercedemo.AppExecutors
import com.example.ecommercedemo.R
import com.example.ecommercedemo.ui.common.DataBoundListAdapter
import com.example.ecommercedemo.vo.test2.RowsItem
import com.example.ecommercedemo.databinding.ProductItemBinding

class ProductListAdapter(private val dataBindingComponent: DataBindingComponent,
                         appExecutors: AppExecutors, private val clickCallback: ((RowsItem) -> Unit)?) : DataBoundListAdapter<RowsItem, ProductItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<RowsItem>() {
        override fun areItemsTheSame(oldItem: RowsItem, newItem: RowsItem): Boolean {
            return oldItem.symbol1 == newItem.symbol1
                    && oldItem.symbol2 == newItem.symbol2
        }

        override fun areContentsTheSame(oldItem: RowsItem, newItem: RowsItem): Boolean {
            return oldItem.symbol1 == newItem.symbol1
                    && oldItem.symbol2 == newItem.symbol2
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

    override fun bind(binding: ProductItemBinding, item: RowsItem) {
        binding.product = item
    }
}