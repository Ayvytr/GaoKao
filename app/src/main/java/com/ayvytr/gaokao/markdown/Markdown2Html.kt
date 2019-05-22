package com.ayvytr.gaokao.markdown

import android.os.Bundle
import android.text.Html
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.gaokao.util.loadMarkdown
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.parser.ParserEmulationProfile
import com.vladsch.flexmark.util.builder.Extension
import com.vladsch.flexmark.util.options.MutableDataSet
import kotlinx.android.synthetic.main.activity_markdown2html.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*


class Markdown2Html : BaseMvpActivity<IPresenter>() {
//    private lateinit var mAppSubject: AppSubject

    private var mContent: String? = null

    override fun initExtra() {
//        mAppSubject = intent.getParcelableExtra<AppSubject>(IntentConst.EXTRA_SUBJECT)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val textSize = AppConfig.markdownFontSize().toFloat()
//        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

        status_view.showLoading()


        mContent = "https://raw.githubusercontent.com/Ayvytr/AndroidGitBook/master/Android%E4%BC%98%E5%8C%96.md"
        mContent!!.loadMarkdown(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val options = MutableDataSet()
                    options.setFrom(ParserEmulationProfile.MARKDOWN)
                    options.set(Parser.EXTENSIONS, Arrays.asList(arrayOf<Extension>(TablesExtension.create())))
                    val extensions = mutableListOf(TablesExtension.create())
                    val parser = Parser.builder().extensions(extensions).build()
//                    val parser = Parser.builder(options).build()
                    val renderer = HtmlRenderer.builder().extensions(extensions).build()
//                    val renderer = HtmlRenderer.builder(options).build()

                    val document = parser.parse(response.body()!!.string())
                    val html = renderer.render(document)

                    runOnUiThread {
//                        web_view.loadData(html, "text/html", "utf-8")
                        status_view.showContent()

//                        ARouter.getInstance().build(WebConstant.WEB)
//                            .withString(WebConstant.EXTRA_DATA, html)
//                            .withBoolean(WebConstant.EXTRA_IS_DATA, true)
//                            .navigation(getContext())
                        tv_content.setText(Html.fromHtml(html))
                    }
                } else {
                    runOnUiThread {
                        status_view.showError(response.message())
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                status_view.showError(e.toString())
            }
        })
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun getContentViewRes(): Int {
        return com.ayvytr.gaokao.R.layout.activity_markdown2html
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}
