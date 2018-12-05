package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.AttentionActivity;
import com.bw.movie.activity.FeedBackActivity;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.MessagesActivity;
import com.bw.movie.activity.TicketRecordActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

public class MineFragmentPresenter extends AppDelegate implements View.OnClickListener {
    private SimpleDraweeView sd;
    private TextView tv_name;
    private String nickName;
    private String headPath;
    private String headPic;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();
//        SharedPreferencesUtils.putBoolean(context, "isloginout", false);
        sd = (SimpleDraweeView) get(R.id.sd);
        tv_name = (TextView) get(R.id.tv_name);
        setClick(this, R.id.sd, R.id.tv_name, R.id.rl1, R.id.rl2, R.id.rl4, R.id.rl5, R.id.iv_messages, R.id.rl3);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sd:
                //图片
                String tv_phone2 = SharedPreferencesUtils.getString(context, "tv_phone2");
                if (!TextUtils.isEmpty(tv_phone2)) {
                    toast("您已登录成功");
                    return;
                } else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }


                break;
            case R.id.tv_name:
                //文字
                String nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (!TextUtils.isEmpty(nickName)) {
                    toast("您已登录过了");
                } else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

                break;
            case R.id.rl1:
                //我的信息
                context.startActivity(new Intent(context, UserInfoActivity.class));
                break;
            case R.id.rl2:
                //我的关注
                context.startActivity(new Intent(context, AttentionActivity.class));
                break;
            case R.id.rl3:
                context.startActivity(new Intent(context, TicketRecordActivity.class));
                //购票记录
                break;
            case R.id.rl4:
                //意见反馈
                context.startActivity(new Intent(context, FeedBackActivity.class));
                break;
            case R.id.rl5:
                //最新版本
                doVersions();
                break;
            case R.id.iv_messages:
                //系统消息
                context.startActivity(new Intent(context, MessagesActivity.class));
                break;

        }
    }


    public void onResume() {
        nickName = SharedPreferencesUtils.getString(context, "nickName");
        if (!TextUtils.isEmpty(nickName)) {
            tv_name.setText(nickName);
        } else {
            tv_name.setText("未登录");

        }
        headPic = SharedPreferencesUtils.getString(context, "headPic");
        headPath = SharedPreferencesUtils.getString(context, "headPath");
        if (!TextUtils.isEmpty(headPath)) {
            sd.setImageURI(headPath);
        } else {
            if (!TextUtils.isEmpty(headPic)) {
                sd.setImageURI(headPic);
            } else {
                sd.setImageResource(R.mipmap.logo);
            }

        }
    }

    //最新版本
    private void doVersions() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> mapHead = new HashMap<>();
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        mapHead.put("ak", "0110010010000");
        new HttpHelper().getHead("/movieApi/tool/v1/findNewVersion", null, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("查询成功")) {
                    toast("当前版本已经是最新版本了，无需更新");
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
