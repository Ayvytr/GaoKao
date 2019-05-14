package com.ayvytr.gaokao

import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        startActivity<MainActivity>()
        finish()
    }
}
