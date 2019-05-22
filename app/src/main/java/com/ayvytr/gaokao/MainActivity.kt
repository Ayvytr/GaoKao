package com.ayvytr.gaokao

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ayvytr.gaokao.fragment.MainVpFragment
import com.ayvytr.gaokao.markdown.Markdown2Html
import com.ayvytr.ktx.ui.switchFragment
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(findViewById(R.id.toolbar))
        mCurrentFragment = switchFragment(MainVpFragment::class.java, R.id.fl_main, mCurrentFragment)

        startActivity<Markdown2Html>()
    }
}
