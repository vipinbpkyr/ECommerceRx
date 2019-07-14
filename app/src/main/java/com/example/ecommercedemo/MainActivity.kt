package com.example.ecommercedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.ecommercedemo.ui.product.ProductViewModel
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProductViewModel::class.java)


    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()


    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu?.findItem(R.id.badge)
        MenuItemCompat.setActionView(item, R.layout.actionbar_cart_layout)
        val notifCount = MenuItemCompat.getActionView(item) as LinearLayout

        car_count = notifCount.findViewById<View>(R.id.count) as TextView

// FIXME TODO
        productViewModel.observeCartCount().observe(this, Observer { result ->
            //            Toast.makeText(this,
//            "cart changes $result", Toast.LENGTH_LONG).show()
            car_count.text = "$result"
        })

        return super.onCreateOptionsMenu(menu)
    }
}
