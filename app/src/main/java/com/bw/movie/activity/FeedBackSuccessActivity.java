package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.FeedBackSuccessPresenter;

public class FeedBackSuccessActivity extends BaseActivityPresenter<FeedBackSuccessPresenter> {
    @Override
    public Class<FeedBackSuccessPresenter> getClassDelegate() {
        return FeedBackSuccessPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
