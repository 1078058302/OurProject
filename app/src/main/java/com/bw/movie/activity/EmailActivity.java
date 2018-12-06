package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.EmailActivityPresenter;

public class EmailActivity extends BaseActivityPresenter<EmailActivityPresenter> {
    @Override
    public Class<EmailActivityPresenter> getClassDelegate() {
        return EmailActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
