package com.ayvytr.commonlibrary

import android.content.Context
import android.content.SharedPreferences
import com.ayvytr.commonlibrary.bean.AppSubject

/**
 * @author admin
 */
object AppConfig {
    @JvmField
    val isDebug = BuildConfig.DEBUG
    lateinit var context: Context
    lateinit var settingsSp: SharedPreferences

    const val PREFS_SETTINGS = "prefs_settings"

    const val SETTINGS_MARKDOWN_FONT_SIZE = "settings_mfz"

    const val DEFAULT_MARKDOWN_FONT_SIZE = 14

    fun init(context: Context) {
        this.context = context
        settingsSp = context.getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE)
    }

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

    fun markdownFontSize(): Int {
        return settingsSp.getInt(SETTINGS_MARKDOWN_FONT_SIZE, DEFAULT_MARKDOWN_FONT_SIZE)
    }

    fun setMarkdownFontSize(size: Int) {
        settingsSp.edit()
            .putInt(SETTINGS_MARKDOWN_FONT_SIZE, size)
            .apply()
    }
}
