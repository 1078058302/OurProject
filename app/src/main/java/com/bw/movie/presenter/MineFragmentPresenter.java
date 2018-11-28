package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class MineFragmentPresenter extends AppDelegate implements View.OnClickListener {
    private SimpleDraweeView sd;
    private TextView tv_name;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();

        sd = (SimpleDraweeView) get(R.id.sd);
        tv_name = (TextView) get(R.id.tv_name);
        setClick(this, R.id.sd, R.id.tv_name);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sd:
                //图片
                goActivity();
                break;

            case R.id.tv_name:
                //文字
                goActivity();
                break;

        }
    }

    private void goActivity() {
        String phone = SharedPreferencesUtils.getString(context, "phone");
        if (TextUtils.isEmpty(phone)) {
            //如果手机号为空就跳转到登陆页面
            context.startActivity(new Intent(context, LoginActivity.class));
        } else {
            //否则跳转到用户信息页面
            context.startActivity(new Intent(context, UserInfoActivity.class));
        }
    }
}
