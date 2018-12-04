package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.CinemaCityActivityPresenter;
//33
public class CinemaCityActivity extends BaseActivityPresenter <CinemaCityActivityPresenter>{


    @Override
    public Class getClassDelegate() {
        return CinemaCityActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
