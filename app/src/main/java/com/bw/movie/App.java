package com.bw.movie;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class App extends MultiDexApplication {
    private static App mainApp;

    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //==============================
        mWxApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxb3852e6a6b7d9516");

    }

    public static App getMainApp() {
        mainApp = new App();
        return mainApp;
    }


}
