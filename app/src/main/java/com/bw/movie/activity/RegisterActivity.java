package com.bw.movie.activity;

import android.content.Context;


import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.RegisterActivityPresenter;


public class RegisterActivity extends BaseActivityPresenter<RegisterActivityPresenter> {
    private Context context;

    @Override
    public Class<RegisterActivityPresenter> getClassDelegate() {
        return RegisterActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        this.context = context;
        this.context=context;
        delegate.setContext(context);
    }






}
