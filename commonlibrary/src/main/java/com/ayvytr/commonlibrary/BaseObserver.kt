package com.ayvytr.commonlibrary


import com.ayvytr.mvp.BasePresenter
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author admin
 */

fun Throwable.toVisibleError(): String {
    var msg = ""
    if (this is UnknownHostException) {
        //无网络的情况，或者主机挂掉了。返回，对应消息  Unable to resolve host "m.app.haosou.com": No address associated with hostname
        msg = "网络不可用/主机无法识别"
    } else if (this is ConnectException || this is SocketTimeoutException || this is SocketException) {
        //连接超时等
        msg = "连接超时"
    } else if (this is HttpException) {
        //HTTP 504 Unsatisfiable Request (only-if-cached)
        //这个错误是没有网络又没有缓存报的异常
        //TODO: 尝试在没有网络的时候就判断是否有缓存，没有缓存直接抛异常终止请求.
        if (this.code() == 504 && this.message == "HTTP 504 Unsatisfiable Request (only-if-cached)") {
            msg = "网络不可用/主机无法识别"
        }
    } else {
        msg = toString()
    }

    return msg
}

abstract class BaseObserver<T> : Observer<T> {
    private var basePresenter: BasePresenter<*, *> ?=null

    constructor()

    /**
     * 委托BasePresenter来处理错误，比如显示错误等.
     * 如果使用无参构造，并且又想处理错误，请重写 [.onError] 来自行处理错误
     *
     * @param basePresenter [BasePresenter]
     */
    constructor(basePresenter: BasePresenter<*, *>) {
        this.basePresenter = basePresenter
    }

    override fun onSubscribe(d: Disposable) {}

    override fun onError(e: Throwable) {
        basePresenter?.handlerErrorMessage(e.toVisibleError())
    }

    override fun onComplete() {
    }
}
