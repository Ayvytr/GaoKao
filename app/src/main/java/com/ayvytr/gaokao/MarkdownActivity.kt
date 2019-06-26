package com.ayvytr.gaokao

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_markdown.*


class MarkdownActivity : BaseMvpActivity<IPresenter>() {
    private lateinit var mAppSubject: AppSubject
    private lateinit var toolbar: Toolbar

    override fun initExtra() {
        mAppSubject = intent.getParcelableExtra<AppSubject>(IntentConst.EXTRA_SUBJECT)
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(mAppSubject.name)

        markdown_view.loadMarkdownFromUrl(mAppSubject.url)
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
