package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.UserInfoActivityPresenter;

public class UserInfoActivity extends BaseActivityPresenter<UserInfoActivityPresenter> {
    @Override
    public Class<UserInfoActivityPresenter> getClassDelegate() {
        return UserInfoActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

}
