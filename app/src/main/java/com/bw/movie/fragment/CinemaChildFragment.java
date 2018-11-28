package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.CinemaChildFragmentPresenter;
import com.bw.movie.presenter.CinemaFragmentPresenter;

public class CinemaChildFragment extends BaseFragmentPresenter<CinemaChildFragmentPresenter> {
    @Override
    public Class<CinemaChildFragmentPresenter> getClassDelegate() {
        return CinemaChildFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
