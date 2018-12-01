package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.NiChengActivity;
import com.bw.movie.mvp.model.NiChengBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class NiChengActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText et_shuru;
    private String nickName;
    private int sex;
    private String email;
    private String trim;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nicheng;
    }

    @Override
    public void initData() {
        super.initData();
        et_shuru = (EditText) get(R.id.et_shuru);
        setClick(this, R.id.iv_fanhui, R.id.confim);
        nickName = SharedPreferencesUtils.getString(context, "nickName");
        sex = SharedPreferencesUtils.getInt(context, "sex");
        email = SharedPreferencesUtils.getString(context, "email");
        et_shuru.setText(nickName);

    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_fanhui:
                ((NiChengActivity) context).finish();
                break;
            case R.id.confim:
                //确定
                doHttp();
                break;
        }
    }

    private void doHttp() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        //获取输入框的值
        trim = et_shuru.getText().toString().trim();
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        m.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("nickName", trim);
        map.put("sex", sex + "");
        map.put("email", email);
        new HttpHelper().post(m, "/movieApi/user/v1/verify/modifyUserInfo", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                toast(data);
                Gson gson = new Gson();
                NiChengBean niChengBean = gson.fromJson(data, NiChengBean.class);
                if ("0000".equals(niChengBean.getStatus())) {
                    toast("修改成功");
                    SharedPreferencesUtils.putString(context, "nickName", trim);
                    ((NiChengActivity) context).finish();
                } else {
                    toast("修改失败");
                    SharedPreferencesUtils.putString(context, "nickName", "");
                    ((NiChengActivity) context).finish();

                }

            }

            @Override
            public void fail(String error) {

            }
        });

    }
}
