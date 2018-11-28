package com.bw.movie.activity;


import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.LoginActivityPresenter;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter> {
    @Override
    public Class<LoginActivityPresenter> getClassDelegate() {
        return LoginActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
