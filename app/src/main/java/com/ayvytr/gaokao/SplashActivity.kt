package com.ayvytr.gaokao

import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private lateinit var mDisposable: Disposable

    override fun onStart() {
        super.onStart()
        mDisposable = Observable.timer(500, TimeUnit.MILLISECONDS)
            .subscribe {
                startActivity<MainActivity>()
                finish()
            }
    }

    override fun onStop() {
        super.onStop()
        if (!mDisposable.isDisposed) {
            mDisposable.dispose()
        }
    }
}
