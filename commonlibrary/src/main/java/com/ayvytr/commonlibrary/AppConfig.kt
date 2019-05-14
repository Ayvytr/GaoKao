package com.ayvytr.commonlibrary

import com.ayvytr.commonlibrary.bean.Subject

/**
 * @author admin
 */
object AppConfig {
    var isDebug = BuildConfig.DEBUG

    //base url


    fun getSubjects(): ArrayList<Subject> = arrayListOf(
        Subject(0, "语文"),
        Subject(1, "数学"),
        Subject(2, "英语"),
        Subject(3, "物理"),
        Subject(4, "化学"),
        Subject(5, "生物"),
        Subject(6, "历史"),
        Subject(7, "地理"),
        Subject(8, "政治")
    )
}
