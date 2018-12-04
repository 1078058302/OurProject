package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.bw.movie.R;
import com.bw.movie.activity.ErrorActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.StartActivity;
import com.bw.movie.activity.WelcomeActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.NetworkUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;


public class StartActivityPresenter extends AppDelegate {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            switch (msg.what) {
                case 0:
                    //判断是否有网
                    if (NetworkUtils.isConnected(context)) {
                        //存储当前有网时的状态
                        SharedPreferencesUtils.putBoolean(context, "isJump", true);
                        context.startActivity(new Intent(context, WelcomeActivity.class));
                        handler.removeCallbacksAndMessages(null);
                        ((StartActivity) context).finish();
                    } else {
                        //没有网跳无网页面并保存当前的状态
                        SharedPreferencesUtils.putString(context, "wel", "0");
                        context.startActivity(new Intent(context, ErrorActivity.class));
                        handler.removeCallbacksAndMessages(null);
                        ((StartActivity) context).finish();
                    }
                    break;
                case 1:
                    //判断是否有网
                    if (NetworkUtils.isConnected(context)) {
                        context.startActivity(new Intent(context, MainActivity.class));
                        handler.removeCallbacksAndMessages(null);
                        ((StartActivity) context).finish();
                    } else {
                        //没有网跳无网页面并保存当前的状态
                        SharedPreferencesUtils.putString(context, "main", "1");
                        context.startActivity(new Intent(context, ErrorActivity.class));
                        handler.removeCallbacksAndMessages(null);
                        ((StartActivity) context).finish();
                    }
                    break;
            }
        }

    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false)
                .build((StartActivity) context).apply();

        Boolean isFirst = SharedPreferencesUtils.getBoolean(context, "isFirst");
        //如果进来过就直接进入主Activity
        if (isFirst) {
            handler.sendEmptyMessageDelayed(1, 2000);
        } else {
            //否则进入欢迎页
            handler.sendEmptyMessageDelayed(0, 2000);
        }

    }

    public void setContext(Context context) {
        this.context = context;
    }


}
