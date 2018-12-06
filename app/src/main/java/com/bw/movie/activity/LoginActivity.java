package com.bw.movie.activity;


import android.content.Context;
import android.view.KeyEvent;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.LoginActivityPresenter;
import com.bw.movie.utils.SharedPreferencesUtils;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter> {
    private Context context;

    @Override
    public Class<LoginActivityPresenter> getClassDelegate() {
        return LoginActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        this.context=context;
        delegate.setContext(context);
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }

    //点击按钮时回调
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Boolean isBack = SharedPreferencesUtils.getBoolean(context, "issack");
//        if (isBack) {
//            //置反
//            SharedPreferencesUtils.putBoolean(context, "issack", false);
//            finish();
//        }
//
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SharedPreferencesUtils.putBoolean(this, "isback", true);
        }
        return true;
    }
}
