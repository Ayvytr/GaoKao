package com.ayvytr.commonlibrary.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.ayvytr.commonlibrary.R
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_tbs_web_view.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@Route(path = WebConstant.TBS_WEB)
class TbsWebViewActivity : BaseMvpActivity<IPresenter>() {

    private var mUrl: String? = null
    private var mTitle: String? = null
    private var mUseWebTitle: Boolean = false

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            if (!webview.canGoBack()) {
                finish()
            } else {
                webview.goBack()
            }
        }

        webview.webChromeClient = object: com.tencent.smtt.sdk.WebChromeClient() {
            override fun onReceivedTitle(p0: com.tencent.smtt.sdk.WebView?, p1: String?) {
                if (mUseWebTitle) {
                    toolbar.title = title
                }
            }
        }
        webview.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                p0?.loadUrl(p1)
                return true
            }
        }
        webview.loadUrl(mUrl)
//        webview.loadData("file:///storage/emulated/0/0a/心理学原理.威廉_詹姆斯.pdf", "text/pdf", "UTF-8")
    }

    override fun initExtra() {
        mUrl = intent.getStringExtra(WebConstant.EXTRA_URL)
        mTitle = intent.getStringExtra(WebConstant.EXTRA_TITLE)
        mUseWebTitle = intent.getBooleanExtra(WebConstant.EXTRA_USE_WEB_TITLE, true)
    }
    override fun getContentViewRes(): Int {
        return R.layout.activity_tbs_web_view
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
            return
        }

        super.onBackPressed()
    }
}
