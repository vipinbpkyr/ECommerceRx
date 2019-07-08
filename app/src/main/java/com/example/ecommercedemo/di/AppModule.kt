package com.example.ecommercedemo.di

import android.app.Application
import androidx.room.Room
import com.example.ecommercedemo.db.AppDb
import com.example.ecommercedemo.network.AppInterceptor
import com.example.ecommercedemo.network.AppService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideAppService(): AppService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(OkHttpClient.Builder().addInterceptor(AppInterceptor()).build())
            .build()
            .create(AppService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        return Room
            .databaseBuilder(app, AppDb::class.java, "ecomm.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
