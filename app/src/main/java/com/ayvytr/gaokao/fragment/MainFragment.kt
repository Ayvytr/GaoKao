package com.ayvytr.gaokao.fragment

import android.os.Bundle
import com.ayvytr.gaokao.R
import com.ayvytr.gaokao.adapter.MainFragmentAdapter
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author admin
 */
class MainFragment : BaseMvpFragment<IPresenter>() {
    private lateinit var mAdapter: MainFragmentAdapter

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = MainFragmentAdapter(context!!)
        rv_main.adapter = mAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_main
    }
}
