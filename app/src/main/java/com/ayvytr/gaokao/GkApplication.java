package com.ayvytr.gaokao;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.AppConfig;

/**
 * @author admin
 */
public class GkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        if(AppConfig.isDebug) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
