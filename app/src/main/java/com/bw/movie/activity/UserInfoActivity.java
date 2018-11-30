package com.bw.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        delegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.onResume();
    }


}
