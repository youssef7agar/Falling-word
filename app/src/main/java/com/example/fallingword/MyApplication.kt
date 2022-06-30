package com.example.fallingword

import android.app.Application
import com.example.fallingword.di.component.ApplicationComponent
import com.example.fallingword.di.component.DaggerApplicationComponent
import timber.log.Timber

class MyApplication : Application() {

    private val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        provideAppComponent().inject(this)
        Timber.plant(Timber.DebugTree())
    }

    fun provideAppComponent() = appComponent
}