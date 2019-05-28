package com.ayvytr.gaokao

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.commonlibrary.util.isUrl
import com.ayvytr.mvp.IPresenter
import com.ayvytr.network.ApiClient
import com.ayvytr.rxlifecycle.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_markdown_formula.*
import kotlinx.android.synthetic.main.activity_markwon.status_view
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class MarkdownFormulaActivity : BaseMvpActivity<IPresenter>() {
    private lateinit var mAppSubject: AppSubject
    private lateinit var toolbar: Toolbar

    override fun initExtra() {
        mAppSubject = intent.getParcelableExtra<AppSubject>(IntentConst.EXTRA_SUBJECT)
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(mAppSubject.name)

        val textSize = AppConfig.markdownFontSize().toFloat()
//        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

        status_view.showLoading()

        if (mAppSubject.url!!.isUrl()) {
            val okHttpClient = ApiClient.getInstance().okHttpClient
            val request = Request.Builder()
                .url(mAppSubject.url)
                .build()
            okHttpClient.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            status_view.showError("加载页面失败:${e}")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            if(response.isSuccessful) {
                                status_view.showContent()
                                flexible_tv.setText(response.body()!!.string())
                            } else {
                                status_view.showError("${response.code()} ${response.message()}")
                            }
                        }
                    }
                })
        } else {
            status_view.showError()
        }
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_markdown_formula
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}
