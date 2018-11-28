package com.bw.movie.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.RegisterBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.service.EncryptUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText et_nicheng, et_sex, et_date, et_phone, et_email, et_pwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {
        super.initData();
        et_nicheng = (EditText) get(R.id.et_nicheng);
        et_sex = (EditText) get(R.id.et_sex);
        et_date = (EditText) get(R.id.et_date);
        et_phone = (EditText) get(R.id.et_phone);
        et_email = (EditText) get(R.id.et_email);
        et_pwd = (EditText) get(R.id.et_pwd);
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
        String etEmail = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(etEmail)) {
            toast("请输入邮箱");
            return;
        }

        String etPwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(etPwd)) {
            toast("请输入登陆密码");
            return;
        }
        String encrypt = EncryptUtil.encrypt(etPwd);
//        Log.i("杀杀杀", encrypt);
        Map<String, String> map = new HashMap<>();
        map.put("nickName", etNicheng);
        map.put("sex",etSex);
        map.put("birthday", etDate);
        map.put("phone", etPhone);
        map.put("email", etEmail);
        map.put("pwd", encrypt);

//        map.size();
        Log.i("RegisterActivit", "doRegister: "+etNicheng+"="+etSex+"="+etDate+"="+etPhone+""+etEmail+"="+encrypt);

        new HttpHelper().post("/movieApi/user/v1/registerUser", map).result(new HttpListener() {
            @Override
            public void success(String data) {

                Toast.makeText(context, data+"", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(data, RegisterBean.class);
                Log.i("面膜", "success: "+registerBean.getStatus());
                if ("0000".equals(registerBean.getStatus())) {
                    toast("注册成功");
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
