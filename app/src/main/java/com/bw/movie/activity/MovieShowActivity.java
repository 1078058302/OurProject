package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.MovieShowActivityPresenter;

/*
 * 电影展示页
   * insert zhang
   * 2018-11-28
 * */
public class MovieShowActivity extends BaseActivityPresenter<MovieShowActivityPresenter> {
    @Override
    public Class<MovieShowActivityPresenter> getClassDelegate() {
        return MovieShowActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
