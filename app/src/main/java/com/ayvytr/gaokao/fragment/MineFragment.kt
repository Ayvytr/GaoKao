package com.ayvytr.gaokao.fragment

import android.os.Bundle
import com.ayvytr.gaokao.R
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment

/**
 * @author admin
 */
class MineFragment : BaseMvpFragment<IPresenter>() {

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_mine
    }
}
