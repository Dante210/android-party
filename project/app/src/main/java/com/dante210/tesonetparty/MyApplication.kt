package com.dante210.tesonetparty

import android.app.Application
import com.dante210.tesonetparty.modules.signInModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(signInModule)
        }
    }
}