package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.SexActivityPresenter;

public class SexActivity extends BaseActivityPresenter<SexActivityPresenter> {
    @Override
    public Class<SexActivityPresenter> getClassDelegate() {
        return SexActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}

