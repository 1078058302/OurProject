package com.bw.movie.activity;


import android.content.Context;
import android.view.KeyEvent;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.LoginActivityPresenter;
import com.bw.movie.utils.SharedPreferencesUtils;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter> {
    private Context context;

    @Override
    public Class<LoginActivityPresenter> getClassDelegate() {
        return LoginActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        this.context=context;
        delegate.setContext(context);
    }

}
