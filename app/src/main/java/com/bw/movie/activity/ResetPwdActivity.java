package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.ResetPwdActivityPresenter;

public class ResetPwdActivity extends BaseActivityPresenter<ResetPwdActivityPresenter> {
    @Override
    public Class<ResetPwdActivityPresenter> getClassDelegate() {
        return ResetPwdActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
