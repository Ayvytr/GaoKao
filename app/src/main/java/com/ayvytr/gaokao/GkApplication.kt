package com.ayvytr.gaokao

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.network.ApiClient
import com.tencent.smtt.sdk.QbSdk

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

        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}
