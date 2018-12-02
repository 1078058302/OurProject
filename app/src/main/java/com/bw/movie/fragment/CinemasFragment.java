package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.CineamsFragmentPresenter;

public class CinemasFragment extends BaseFragmentPresenter<CineamsFragmentPresenter> {
    @Override
    public Class<CineamsFragmentPresenter> getClassDelegate() {
        return CineamsFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
