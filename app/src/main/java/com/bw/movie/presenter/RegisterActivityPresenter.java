package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaCityActivity;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.RegisterActivity;
import com.bw.movie.mvp.model.RegisterBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.service.EncryptUtil;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText et_nicheng, et_sex, et_date, et_phone, et_email, et_pwd, et_pwd2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((RegisterActivity) context)
                .apply();
        et_nicheng = (EditText) get(R.id.et_nicheng);
        et_sex = (EditText) get(R.id.et_sex);
        et_date = (EditText) get(R.id.et_date);
        et_phone = (EditText) get(R.id.et_phone);
        et_email = (EditText) get(R.id.et_email);
        et_pwd = (EditText) get(R.id.et_pwd);
        et_pwd2 = (EditText) get(R.id.et_pwd2);
        setClick(this, R.id.bt_register);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_register:
                //注册
                doRegister();
                break;

        }
    }

    //注册
    private void doRegister() {

        String etNicheng = et_nicheng.getText().toString().trim();
        if (TextUtils.isEmpty(etNicheng)) {
            toast("请输入昵称");
            return;
        }
        String etSex = et_sex.getText().toString().trim();
        if (TextUtils.isEmpty(etSex)) {
            toast("请输入性别");
            return;
        }
        String etDate = et_date.getText().toString().trim();
        if (TextUtils.isEmpty(etDate)) {
            toast("请输入出生日期");
            return;
        }
        String etPhone = et_phone.getText().toString();
        if (TextUtils.isEmpty(etPhone)) {
            toast("手机号不能为空");
            return;
        }
        final String etEmail = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(etEmail)) {
            toast("请输入邮箱");
            return;
        }

        String etPwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(etPwd)) {
            toast("请输入登陆密码");
            return;
        }

        String etPwd2 = et_pwd2.getText().toString().trim();
        if (!etPwd2.equals(etPwd)) {
            toast("请保持密码一致");
            return;
        }
        String encrypt = EncryptUtil.encrypt(etPwd);

        Map<String, String> map = new HashMap<>();
        map.put("nickName", etNicheng);
        if (etSex.equals("男")) {
            map.put("sex", "1");
        } else {
            map.put("sex", "2");
        }
        map.put("birthday", etDate);
        map.put("phone", etPhone);
        map.put("email", etEmail);
        map.put("pwd", encrypt);
        map.put("pwd2", encrypt);
        new HttpHelper().post(null, "/movieApi/user/v1/registerUser", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(data, RegisterBean.class);
                if ("0000".equals(registerBean.getStatus())) {
                    toast("注册成功");
                    SharedPreferencesUtils.putString(context, "email", etEmail);
                    ((RegisterActivity) context).finish();

                } else {
                    toast("注册失败");
                }


            }

            @Override
            public void fail(String error) {

            }
        });


    }
}

