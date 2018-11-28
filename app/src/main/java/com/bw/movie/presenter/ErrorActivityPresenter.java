package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.activity.ErrorActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.WelcomeActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.NetworkUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;

public class ErrorActivityPresenter extends AppDelegate {



    @Override
    public int getLayoutId() {
        return R.layout.activity_error;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((ErrorActivity) context).apply();
        get(R.id.tv_refresh).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //当点击时判断是否有网
                boolean connected = NetworkUtils.isConnected(context);
                //有网试
                if (connected) {

                    //获取当前的状态
                    String a = SharedPreferencesUtils.getString(context, "wel");
                    String b = SharedPreferencesUtils.getString(context, "main");
                    toast("wel"+a);
                    toast("main"+b);
                    //0表示欢迎页的状态
                    if ("0".equals(a)) {
                        context.startActivity(new Intent(context, WelcomeActivity.class));
                        //重新将欢迎页的赋值 不然出现无法销毁当前的视图
                        SharedPreferencesUtils.putString(context, "wel", "1");
                        //再次保存状态
                        SharedPreferencesUtils.putBoolean(context, "isJump", true);
                        ((ErrorActivity) context).finish();
                    }

                    Boolean isJump = SharedPreferencesUtils.getBoolean(context, "isJump");
                    toast(isJump+"");
                    if ("1".equals(b) && SharedPreferencesUtils.getBoolean(context, "isJump")) {

                        context.startActivity(new Intent(context, MainActivity.class));
                        ((ErrorActivity) context).finish();
                    }

                } else {
                    toastData("亲,您没有连网");
                }

            }
        });

    }


    public void setContext(Context context) {
        this.context = context;
    }
}
