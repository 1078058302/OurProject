package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.CommentActivityPresenter;

public class CommentActivity extends BaseActivityPresenter<CommentActivityPresenter> {

    @Override
    public Class<CommentActivityPresenter> getClassDelegate() {
        return CommentActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
