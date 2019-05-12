package com.dante210.tesonetparty

import android.app.Activity
import android.app.Application
import androidx.navigation.Navigation.findNavController
import com.dante210.tesonetparty.api.TesonetApi
import com.dante210.tesonetparty.api.TesonetInterceptor
import com.dante210.tesonetparty.repositories.UserRemoteRepository
import com.dante210.tesonetparty.ui.SignInViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("unused")
class MyApplication : Application() {
  companion object {
    private val okHttpClient = {
      val TIMEOUT_SEC: Long = 10

      val httpClient = OkHttpClient().newBuilder()
        .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)

      httpClient.addInterceptor(TesonetInterceptor())
      httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
      httpClient.build()
    }

    val retrofit: Retrofit =
      Retrofit.Builder()
        .client(okHttpClient())
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger()
      androidContext(this@MyApplication)
      modules(
        TesonetApi.module,
        UserRemoteRepository.module,
        SignInViewModel.module
      )
    }
  }
}