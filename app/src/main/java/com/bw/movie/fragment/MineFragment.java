package com.bw.movie.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
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
}
