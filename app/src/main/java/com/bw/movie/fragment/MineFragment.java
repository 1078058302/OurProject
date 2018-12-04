package com.bw.movie.fragment;


import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.MineFragmentPresenter;


public class MineFragment extends BaseFragmentPresenter<MineFragmentPresenter> {


    @Override
    public Class<MineFragmentPresenter> getClassDelegate() {
        return MineFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.onResume();
    }


}
