package com.ayvytr.gaokao.fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.MenuItem
import com.ayvytr.gaokao.R
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_main_vp.*

/**
 * @author admin
 */
class MainVpFragment : BaseMvpFragment<IPresenter>(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun getContentViewRes(): Int {
        return R.layout.fragment_main_vp
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initExtra() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        val fragments = arrayListOf(MainFragment(), MainFragment(), MineFragment())

        vp.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }
        }
        //BottomNavigation 部分自定义设置: https://blog.csdn.net/xiongya8888/article/details/83825192
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }


    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mid_home -> {
                vp.currentItem = 0
                return true
            }
            R.id.mid_more -> {
                vp.currentItem = 1
                return true
            }
            R.id.mid_mine -> {
                vp.currentItem = 2
                return true
            }
        }

        return false
    }

}