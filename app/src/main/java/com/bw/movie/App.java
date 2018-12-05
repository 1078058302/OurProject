package com.bw.movie;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //==============================
    }
}
