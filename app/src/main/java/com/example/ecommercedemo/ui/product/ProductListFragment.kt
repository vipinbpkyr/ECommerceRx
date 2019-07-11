package com.example.ecommercedemo.ui.product


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.android.example.github.binding.FragmentDataBindingComponent
import com.example.ecommercedemo.AppExecutors
import com.example.ecommercedemo.R
import com.example.ecommercedemo.databinding.FragmentProductListBinding
import com.example.ecommercedemo.di.Injectable
import com.example.ecommercedemo.util.autoCleared
import com.example.ecommercedemo.vo.Resource
import com.example.ecommercedemo.vo.ProductResponse
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var productViewModel: ProductViewModel
    var binding by autoCleared<FragmentProductListBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    @Inject
    lateinit var appExecutors: AppExecutors
    var adapter by autoCleared<ProductListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        val prAdapter = ProductListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ){ product ->
            val extras = bundleOf("title" to product.name,
                "description" to product.description,
                "image" to product.image)
            navController().navigate(R.id.productDetailsFragment, extras)
        }

        recyclerView.adapter = prAdapter
        adapter = prAdapter
        productViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            processResponse(result)
        })
        productViewModel.fetchData()
    }

    private fun processResponse(resp: Resource<ProductResponse>) {
//        Timber.d("xxx processResponse status ${resp.status} data ${resp.data} message ${resp.message}")
        adapter.submitList(resp.data?.rows)

    }


    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}
