package com.bw.movie.presenter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.bw.movie.R;
import com.bw.movie.activity.EmailActivity;
import com.bw.movie.mvp.model.NiChengBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class EmailActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText et_email;
    private String nickName;
    private int sex;
    private String email;

    @Override
    public int getLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    public void initData() {
        super.initData();
        setClick(this, R.id.iv_fanhui3, R.id.tv_confim);
        et_email = (EditText) get(R.id.et_email);
        nickName = SharedPreferencesUtils.getString(context, "nickName");
        sex = SharedPreferencesUtils.getInt(context, "sex");
        email = SharedPreferencesUtils.getString(context, "email");
        et_email.setText(email);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui3:
                //返回
                ((EmailActivity) context).finish();
                break;
            case R.id.tv_confim:
                //确定
                doPost();
                break;
        }
    }

    private void doPost() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        //获取输入框的值
        final String etEmail = et_email.getText().toString().trim();
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        m.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("nickName", nickName);
        map.put("sex", sex + "");
        map.put("email", etEmail);
        new HttpHelper().post(m, "/movieApi/user/v1/verify/modifyUserInfo", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                NiChengBean niChengBean = gson.fromJson(data, NiChengBean.class);
                if ("0000".equals(niChengBean.getStatus())) {
                    toast("修改成功");
                    SharedPreferencesUtils.putString(context, "email", etEmail);
                    ((EmailActivity) context).finish();
                } else {
                    toast("修改失败");
                    SharedPreferencesUtils.putString(context, "email", "");
                    ((EmailActivity) context).finish();

                }

            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
