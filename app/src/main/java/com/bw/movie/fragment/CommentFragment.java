package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.CommentFragmentPresenter;

public class CommentFragment extends BaseFragmentPresenter<CommentFragmentPresenter> {
    @Override
    public Class<CommentFragmentPresenter> getClassDelegate() {
        return CommentFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
