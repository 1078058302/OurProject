package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.CinemaChild1FragmentPresenter;
import com.bw.movie.presenter.CinemaChildFragmentPresenter;

public class CinemaChild1Fragment extends BaseFragmentPresenter<CinemaChild1FragmentPresenter> {
    @Override
    public Class<CinemaChild1FragmentPresenter> getClassDelegate() {
        return CinemaChild1FragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
