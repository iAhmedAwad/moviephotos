package com.cashu.moviephotos.application

import android.app.Application
import android.content.Context

class BaseApplication: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
