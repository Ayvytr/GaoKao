package com.ayvytr.gaokao.fragment

import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.commonlibrary.AppConfig.DEFAULT_MARKDOWN_FONT_SIZE
import com.ayvytr.gaokao.R
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author admin
 */
class MineFragment : BaseMvpFragment<IPresenter>() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val markdownFontSize = AppConfig.markdownFontSize()
        tv_markdown_fontsize.onClick {
            AlertDialog.Builder(context!!)
                .setTitle("选择字体大小")
                .setSingleChoiceItems(
                    R.array.markdownFontSizeArray,
                    markdownFontSize - DEFAULT_MARKDOWN_FONT_SIZE
                ) { dialog, which ->
                    AppConfig.setMarkdownFontSize(which + DEFAULT_MARKDOWN_FONT_SIZE)
                    dialog.dismiss()
                }
                .show()
        }

        context?.let {
            val packageInfo = it.packageManager.getPackageInfo(it.packageName, 0)
            tv_version.text = "Package Name: ${it.packageName} " +
                    "\nVersion Name: ${packageInfo.versionName} " +
                    "\nVersion Code: ${packageInfo.versionCode}"
        }
    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_mine
    }
}
