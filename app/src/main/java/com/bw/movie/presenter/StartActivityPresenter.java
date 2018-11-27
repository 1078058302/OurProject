package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.StartActivity;
import com.bw.movie.activity.WelcomeActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;

public class StartActivityPresenter extends AppDelegate {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    context.startActivity(new Intent(context, WelcomeActivity.class));
                    ((StartActivity)context).finish();
                    break;
                case 1:
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((StartActivity)context).finish();
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
        //如果进来过进直接进入主Activity
        if (isFirst) {
            handler.sendEmptyMessageDelayed(1, 2000);
        } else {
            handler.sendEmptyMessageDelayed(0, 2000);
        }


    }

    public void setContext(Context context) {
        this.context = context;
    }

}
