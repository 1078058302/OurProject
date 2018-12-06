package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.FilmFragmentPresenter;

public class FilmFragment extends BaseFragmentPresenter<FilmFragmentPresenter> {
    @Override
    public Class<FilmFragmentPresenter> getClassDelegate() {
        return FilmFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContxt(context);
    }
}
