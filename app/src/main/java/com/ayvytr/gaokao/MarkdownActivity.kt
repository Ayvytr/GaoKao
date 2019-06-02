package com.ayvytr.gaokao

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.commonlibrary.util.isUrl
import com.ayvytr.ktx.ui.show
import com.ayvytr.mvp.IPresenter
import com.ayvytr.network.ApiClient
import com.ayvytr.rxlifecycle.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_markwon.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onScrollChange
import ru.noties.markwon.AbstractMarkwonPlugin
import ru.noties.markwon.Markwon
import ru.noties.markwon.MarkwonConfiguration
import ru.noties.markwon.ext.latex.JLatexMathPlugin
import ru.noties.markwon.ext.tables.TablePlugin
import ru.noties.markwon.image.AsyncDrawableLoader
import ru.noties.markwon.image.ImagesPlugin
import java.io.IOException




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

        val textSize = AppConfig.markdownFontSize().toFloat()
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

        status_view.showLoading()

        nsv.onScrollChange { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            fab_top.show(scrollY != 0)
            if (tv_content.getHeight() <= scrollY + nsv.getHeight()) {   // 如果满足就是到底部了
                fab_bottom.hide()
            } else {
                fab_bottom.show()
            }
        }

        fab_top.onClick {
            nsv.fling(0)
            nsv.fullScroll(View.FOCUS_UP)
        }
        fab_bottom.onClick {
            nsv.fullScroll(View.FOCUS_DOWN)
        }


        val markwon = Markwon.builder(getContext())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
//                    builder.htmlParser(MarkwonHtmlParserImpl.create())
                    builder.linkResolver { view, link ->
                        ARouter.getInstance().build(WebConstant.WEB)
                            .withString(WebConstant.EXTRA_URL, link)
                            .navigation(getContext())
                    }
                }

                override fun configureImages(builder: AsyncDrawableLoader.Builder) {
//                    builder.addSchemeHandler("",
//                        OkHttpSchemeHandler(ApiClient.getInstance().okHttpClient, mContent!!))
                    builder.errorDrawableProvider {
                        getContext().resources.getDrawable(R.drawable.photo_error)
                    }
                    builder.placeholderDrawableProvider {
                        getContext().resources.getDrawable(R.drawable.photo_loading)
                    }
                }
            })
            .usePlugin(TablePlugin.create(getContext()))
            .usePlugin(ImagesPlugin.create(getContext()))
//            .usePlugin(ImagesPlugin.create(getContext()))
//            .usePlugin(ru.noties.markwon.image.okhttp.OkHttpImagesPlugin.create(ApiClient.getInstance().okHttpClient))
//            .usePlugin(OkHttpImagesPlugin.create(ApiClient.getInstance().okHttpClient, mContent))
//            .usePlugin(GifPlugin.create())
            //卡顿，公式加载不出来
//            .usePlugin(JLatexMathPlugin.create(
//                textSize.toFloat()
//            ) { builder ->
//                builder.background(getDrawable2(R.drawable.formula)!!)
//                    .align(JLatexMathDrawable.ALIGN_CENTER)
//                    .fitCanvas(true)
//                    .padding(dip(10))
//            })
            .usePlugin(JLatexMathPlugin.create(textSize))
            .build()

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
                            if (response.isSuccessful) {
                                markwon.setMarkdown(tv_content, response.body()!!.string(), mAppSubject.url!!)
                                status_view.showContent()

//                                L.e(tv_content.height, nsv.height)
//                                if(tv_content.height == nsv.height) {
//                                    fab_top.show()
//                                    fab_bottom.show()
//                                }
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
        return R.layout.activity_markwon
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}
