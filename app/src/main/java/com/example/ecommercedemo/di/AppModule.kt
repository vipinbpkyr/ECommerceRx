package com.example.ecommercedemo.di

import android.app.Application
import android.text.Editable
import androidx.room.Room
import com.commonsware.cwac.saferoom.SQLCipherUtils
import com.example.ecommercedemo.db.AppDb
import com.example.ecommercedemo.db.CartDao
import com.example.ecommercedemo.network.AppInterceptor
import com.example.ecommercedemo.network.AppService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton
import com.commonsware.cwac.saferoom.SafeHelperFactory



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
        // EditText passphraseField;
//        val editable = Editable.Factory.getInstance().newEditable("text")
//        val factory = SafeHelperFactory.fromUser(editable)
//        val state = SQLCipherUtils.getDatabaseState(app, "ecomm23.db")
        return Room
            .databaseBuilder(app, AppDb::class.java, "ecomm2.db")
            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCartDao(db: AppDb): CartDao {
        return db.cartDao()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
