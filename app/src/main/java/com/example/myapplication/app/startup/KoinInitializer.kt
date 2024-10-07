package com.example.myapplication.app.startup

import android.content.Context
import androidx.startup.Initializer
import com.example.myapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

@Suppress("unused")
class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(appModule)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}