package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.MovieDescActivityPresenter;

public class MovieDescActivity extends BaseActivityPresenter<MovieDescActivityPresenter> {

    @Override
    public Class<MovieDescActivityPresenter> getClassDelegate() {
        return MovieDescActivityPresenter.class;
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
