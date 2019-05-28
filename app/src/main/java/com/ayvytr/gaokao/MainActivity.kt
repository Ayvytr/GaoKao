package com.ayvytr.gaokao

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ayvytr.gaokao.fragment.MainNavigationFragment
import com.ayvytr.ktx.ui.switchFragment

class MainActivity : AppCompatActivity() {
    private var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(findViewById(R.id.toolbar))
        mCurrentFragment = switchFragment(MainNavigationFragment::class.java, R.id.fl_main, mCurrentFragment)
    }
}
