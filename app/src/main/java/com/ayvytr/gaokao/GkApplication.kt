package com.ayvytr.gaokao

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.network.ApiClient

/**
 * @author admin
 */
class GkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        if (AppConfig.isDebug) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
        ApiClient.getInstance().init(applicationContext, "https://github.com/ayvytr/")

        AppConfig.init(applicationContext)
    }
}
