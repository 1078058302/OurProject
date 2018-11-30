package com.bw.movie.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.FilmCinemaActivityPresenter;

public class FilmCinemaActivity extends BaseActivityPresenter <FilmCinemaActivityPresenter>{


    @Override
    public Class getClassDelegate() {
        return FilmCinemaActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
