package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.StartActivityPresenter;

public class StartActivity extends BaseActivityPresenter<StartActivityPresenter> {


    @Override
    public Class getClassDelegate() {
        return StartActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
