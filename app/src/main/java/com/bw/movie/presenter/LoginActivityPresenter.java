package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.activity.RegisterActivity;
import com.bw.movie.mvp.view.AppDelegate;

public class LoginActivityPresenter extends AppDelegate implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        setClick(this, R.id.register);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                //注册
                context.startActivity(new Intent(context, RegisterActivity.class));
                break;

        }
    }
}
