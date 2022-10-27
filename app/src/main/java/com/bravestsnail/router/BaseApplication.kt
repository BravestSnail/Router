package com.bravestsnail.router

import android.app.Application
import com.bravestsnail.router.handler.RouterUtils

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RouterUtils.init(this)
    }
}