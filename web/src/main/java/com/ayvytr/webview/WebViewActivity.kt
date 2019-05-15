package com.ayvytr.webview

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_web_view.*

@Route(path = WebConstant.WEB)
class WebViewActivity : BaseMvpActivity<IPresenter>() {

    private lateinit var mAgentWeb: AgentWeb
    private var mUrl: String? = null
    private var mTitle: String? = null
    private var mUseWebTitle: Boolean = false

    private lateinit var mWebViewClient: WebViewClient
    private lateinit var mWebChromeClient: WebChromeClient

    private lateinit var toolbar:Toolbar

    override fun getPresenter(): IPresenter? {
        return null
    }

    private fun initAgentWeb() {
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            if (!mAgentWeb.back()) {
                finish()
            }
        }
        mWebViewClient = object : WebViewClient() {}
        mWebChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                if (mUseWebTitle) {
                    toolbar.title = title
                }
            }
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(flContainer!!,
                               FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                        FrameLayout.LayoutParams.MATCH_PARENT))
            .useDefaultIndicator()
            .setWebViewClient(mWebViewClient)
            .setWebChromeClient(mWebChromeClient)
            .createAgentWeb()
            .ready()
            .go(if (TextUtils.isEmpty(mUrl)) "" else mUrl)
    }

    override fun initExtra() {
        mUrl = intent.getStringExtra(WebConstant.EXTRA_URL)
        mTitle = intent.getStringExtra(WebConstant.EXTRA_TITLE)
        mUseWebTitle = intent.getBooleanExtra(WebConstant.EXTRA_USE_WEB_TITLE, true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initAgentWeb()
        if (!mUseWebTitle) {
            toolbar.title = mTitle
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_web_view
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb.webLifeCycle.onDestroy()
    }
}
