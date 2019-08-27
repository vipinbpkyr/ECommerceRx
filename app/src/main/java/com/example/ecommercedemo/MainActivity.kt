package com.example.ecommercedemo

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.ecommercedemo.ui.product.ProductViewModel
import com.google.firebase.FirebaseApp
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private lateinit var car_count: TextView
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var productViewModel: ProductViewModel

    var mHandlerThread: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        productViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        productViewModel.observeRxCartCount()

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.badge)
        item.setActionView(R.layout.actionbar_cart_layout)
        val notifyCount = item?.actionView

        car_count = notifyCount?.findViewById<View>(R.id.count) as TextView

// FIXME TODO
        productViewModel.cartCount.observe(this, Observer { result ->
            car_count.text = "$result"
        })

        return super.onCreateOptionsMenu(menu)
    }
}
