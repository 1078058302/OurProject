package com.bw.movie.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bw.movie.R;
import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.CinemaDetailActivityPresenter;

public class CinemaDetailActivity extends BaseActivityPresenter<CinemaDetailActivityPresenter>{

    @Override
    public Class<CinemaDetailActivityPresenter> getClassDelegate() {
        return CinemaDetailActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);

    }

    @Override
    public void initView() {
        super.initView();
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

}
