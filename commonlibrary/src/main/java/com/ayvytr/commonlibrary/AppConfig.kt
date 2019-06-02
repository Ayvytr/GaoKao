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
     * TODO: 修正链接
     * 初中
     */
    fun getJuniorSubjects(): ArrayList<AppSubject> = arrayListOf(
        AppSubject(0, "语文", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", false),
        AppSubject(1, "数学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(2, "英语", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E5%88%9D%E4%B8%AD%E8%8B%B1%E8%AF%AD.md", false),
        AppSubject(3, "物理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(4, "化学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(5, "生物", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(6, "历史", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", false),
        AppSubject(7, "地理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", false),
        AppSubject(8, "政治", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/%E6%95%B0%E5%AD%A6.md", false)
    )

    /**
     * TODO: 修正链接
     * 高中
     */
    fun getSeniorSubjects(): ArrayList<AppSubject> = arrayListOf(
        AppSubject(0, "语文", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/type.md", false),
        AppSubject(1, "数学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(2, "英语", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", false),
        AppSubject(3, "物理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(4, "化学", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(5, "生物", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", true),
        AppSubject(6, "历史", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", false),
        AppSubject(7, "地理", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", false),
        AppSubject(8, "政治", "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E6%95%B0%E5%AD%A6.md", false)
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
