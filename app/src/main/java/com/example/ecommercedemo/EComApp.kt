package com.example.ecommercedemo

import android.app.Activity
import android.app.Application
import com.bumptech.glide.request.target.ViewTarget
import com.example.ecommercedemo.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class EComApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
        ViewTarget.setTagId(R.id.glide_tag);

    }

    override fun activityInjector() = dispatchingAndroidInjector
}