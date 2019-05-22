package com.ayvytr.gaokao

import android.os.Bundle
import android.util.TypedValue
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_markwon.*


class MarkdownActivity : BaseMvpActivity<IPresenter>() {
    private lateinit var mAppSubject: AppSubject

    private var mContent: String? = null

    override fun initExtra() {
        mAppSubject = intent.getParcelableExtra<AppSubject>(IntentConst.EXTRA_SUBJECT)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val textSize = AppConfig.markdownFontSize().toFloat()
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

        status_view.showLoading()

    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_markdown
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}
