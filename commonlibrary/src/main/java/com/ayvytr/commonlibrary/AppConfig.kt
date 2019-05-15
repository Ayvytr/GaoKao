package com.ayvytr.commonlibrary

import com.ayvytr.commonlibrary.bean.AppSubject

/**
 * @author admin
 */
object AppConfig {
    @JvmField
    val isDebug = BuildConfig.DEBUG

    //base url


    fun getSubjects(): ArrayList<AppSubject> = arrayListOf(
        AppSubject(0, "语文"),
        AppSubject(1, "数学"),
        AppSubject(2, "英语"),
        AppSubject(3, "物理"),
        AppSubject(4, "化学"),
        AppSubject(5, "生物"),
        AppSubject(6, "历史"),
        AppSubject(7, "地理"),
        AppSubject(8, "政治")
    )
}
