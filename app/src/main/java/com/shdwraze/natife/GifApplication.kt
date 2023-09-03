package com.shdwraze.natife

import android.app.Application
import com.shdwraze.natife.data.AppContainer
import com.shdwraze.natife.data.DefaultAppContainer

class GifApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}