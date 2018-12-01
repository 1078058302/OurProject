package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.NiChengActivityPresenter;

public class NiChengActivity extends BaseActivityPresenter<NiChengActivityPresenter> {
    @Override
    public Class<NiChengActivityPresenter> getClassDelegate() {
        return NiChengActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);

    }
}
