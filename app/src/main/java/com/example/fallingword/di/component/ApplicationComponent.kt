package com.example.fallingword.di.component

import android.content.Context
import com.example.fallingword.MainActivity
import com.example.fallingword.MyApplication
import com.example.fallingword.di.module.AppModule
import com.example.fallingword.di.module.RemoteModule
import com.example.fallingword.di.module.ViewModelModule
import com.example.fallingword.presentation.view.GameFragment
import com.example.fallingword.presentation.view.WelcomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RemoteModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: MyApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: WelcomeFragment)

    fun inject(fragment: GameFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}