package com.bw.movie;

import android.content.Context;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.mvp.presenter.MainActivityPresenter;
//主Activity

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter> {

    @Override
    public Class<MainActivityPresenter> getClassDelegate() {
        return MainActivityPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
