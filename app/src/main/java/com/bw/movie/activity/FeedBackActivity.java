package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.FeedBackActivityPresenter;

public class FeedBackActivity extends BaseActivityPresenter<FeedBackActivityPresenter> {
    @Override
    public Class<FeedBackActivityPresenter> getClassDelegate() {
        return FeedBackActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
