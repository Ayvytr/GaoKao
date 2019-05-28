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

    /**
     * 初中
     */
    fun getJuniorSubjects(): ArrayList<AppSubject> = arrayListOf(
        AppSubject(0, "语文", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(1, "数学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(2, "英语", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(3, "物理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(4, "化学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(5, "生物", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(6, "历史", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(7, "地理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(8, "政治", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md")
    )

    /**
     * 高中
     */
    fun getSeniorSubjects(): ArrayList<AppSubject> = arrayListOf(
        AppSubject(0, "语文", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(1, "数学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(2, "英语", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(3, "物理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(4, "化学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(5, "生物", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(6, "历史", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(7, "地理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md"),
        AppSubject(8, "政治", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md")
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
