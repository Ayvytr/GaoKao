package com.ayvytr.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.constant.Web;
import com.ayvytr.mvp.BaseMvpActivity;
import com.ayvytr.mvp.IPresenter;
import com.ayvytr.webview.R;

public class MainActivity extends BaseMvpActivity {

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.btnJumpWebView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance()
                               .build(Web.WEBVIEW)
                               .withString(Web.EXTRA_URL, "http://www.baidu.com")
                               .withString(Web.EXTRA_TITLE, "标题")
                               .navigation(getContext());
                    }
                });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
