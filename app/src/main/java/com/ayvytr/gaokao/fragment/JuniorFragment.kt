package com.ayvytr.gaokao.fragment

import android.os.Bundle
import com.ayvytr.commonlibrary.AppConfig
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.gaokao.MarkdownActivity
import com.ayvytr.gaokao.MarkdownFormulaActivity
import com.ayvytr.gaokao.R
import com.ayvytr.gaokao.adapter.SubjectAdapter
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_senior.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * @author admin
 */
class JuniorFragment : BaseMvpFragment<IPresenter>() {
    private lateinit var mAdapter: SubjectAdapter

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = SubjectAdapter(context!!, AppConfig.getJuniorSubjects())
        mAdapter.setOnItemClickListener { view, holder, position ->
            val appSubject = mAdapter.getItemAt(position)
            if (appSubject.hasFormula) {
                startActivity<MarkdownActivity>(IntentConst.EXTRA_SUBJECT to appSubject)
            } else {
                startActivity<MarkdownFormulaActivity>(IntentConst.EXTRA_SUBJECT to appSubject)
            }
        }
        rv_main.adapter = mAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_senior
    }
}
