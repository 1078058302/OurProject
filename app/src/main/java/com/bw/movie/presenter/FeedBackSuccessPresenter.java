package com.bw.movie.presenter;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.activity.FeedBackSuccessActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.UltimateBar;

public class FeedBackSuccessPresenter extends AppDelegate {
    @Override
    public int getLayoutId() {
        return R.layout.activity_feedbacksuccess;
    }

    @Override
    public void initData() {
        UltimateBar.newImmersionBuilder().applyNav(false).build((FeedBackSuccessActivity) context).apply();

    }

    public void setContext(Context context) {
        this.context = context;
    }
}
