package com.bw.movie.presenter;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;

public class SexActivityPresenter extends AppDelegate {
    @Override
    public int getLayoutId() {
        return R.layout.activity_sex;
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
