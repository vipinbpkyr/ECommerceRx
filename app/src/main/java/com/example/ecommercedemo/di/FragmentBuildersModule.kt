package com.example.ecommercedemo.di

import com.example.ecommercedemo.ui.product.ProductListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeProductListFragment(): ProductListFragment
}