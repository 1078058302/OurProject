package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.MovieFragmentPresenter;

public class MovieFragment extends BaseFragmentPresenter<MovieFragmentPresenter> {

    @Override
    public Class<MovieFragmentPresenter> getClassDelegate() {
        return MovieFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);

        delegate.setContext(context);
    }

    
}
