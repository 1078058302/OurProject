package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.MovieShowFragmentPresenter;

public class MovieShowFragment extends BaseFragmentPresenter<MovieShowFragmentPresenter> {

    @Override
    public Class<MovieShowFragmentPresenter> getClassDelegate() {
        return MovieShowFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);

        delegate.setContext(context);
    }

    
}
