package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.ErrorActivityPresenter;

public class ErrorActivity extends BaseActivityPresenter<ErrorActivityPresenter> {


    @Override
    public Class getClassDelegate() {
        return ErrorActivityPresenter.class;
    }


    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }


}
