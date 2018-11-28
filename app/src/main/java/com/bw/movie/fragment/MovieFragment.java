package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.ShowMovieFragmentPresenter;

public class MovieFragment extends BaseFragmentPresenter<ShowMovieFragmentPresenter> {

    @Override
    public Class<ShowMovieFragmentPresenter> getClassDelegate() {
        return ShowMovieFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);

        delegate.setContext(context);
    }

    
}
