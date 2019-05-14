package com.ayvytr.gaokao

import android.os.Bundle
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity

class SubjectActivity : BaseMvpActivity<IPresenter>() {
    private lateinit var mAppSubject: AppSubject

    override fun initExtra() {
        mAppSubject = intent.getParcelableExtra<AppSubject>(IntentConst.EXTRA_SUBJECT)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_subject_detail
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}
