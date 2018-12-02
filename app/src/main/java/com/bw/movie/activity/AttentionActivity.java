package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.AttentionActivityPresenter;

public class AttentionActivity extends BaseActivityPresenter<AttentionActivityPresenter> {
    @Override
    public Class<AttentionActivityPresenter> getClassDelegate() {
        return AttentionActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
