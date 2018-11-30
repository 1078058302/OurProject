package com.bw.movie.fragment;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseFragmentPresenter;
import com.bw.movie.presenter.DetailsShowFragmentPresenter;

public class DetailsShowFragment extends BaseFragmentPresenter<DetailsShowFragmentPresenter> {
    @Override
    public Class<DetailsShowFragmentPresenter> getClassDelegate() {
        return DetailsShowFragmentPresenter.class;
    }

    @Override
    public void initView() {
        super.initView();
        delegate.setArguments(getArguments());
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(getActivity());

    }
}
