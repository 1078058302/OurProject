package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.RegisterActivity;
import com.bw.movie.mvp.model.LoginBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.service.EncryptUtil;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginActivityPresenter extends AppDelegate implements View.OnClickListener, View.OnTouchListener {
    private EditText tv_phone, tv_pwd;
    private String tvPhone;
    private String tvPwd;
    private CheckBox cb1, cb2;
    private ImageView eye;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        tv_phone = (EditText) get(R.id.tv_phone);
        tv_pwd = (EditText) get(R.id.tv_pwd);
        cb1 = (CheckBox) get(R.id.cb1);
        cb2 = (CheckBox) get(R.id.cb2);
        //获取数据
        String tvPhone1 = SharedPreferencesUtils.getString(context, "tv_phone");
        String tvPwd1 = SharedPreferencesUtils.getString(context, "tv_pwd");
        if (TextUtils.isEmpty(tvPhone1)) {
            cb1.setChecked(false);
        } else {
            cb1.setChecked(true);
        }
        //设置值
        tv_phone.setText(tvPhone1);
        tv_pwd.setText(tvPwd1);
        setClick(this, R.id.login, R.id.register, R.id.cb1, R.id.cb2);
        eye = (ImageView) get(R.id.eye);
        //图标监听事件
        eye.setOnTouchListener(this);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                //注册
                context.startActivity(new Intent(context, RegisterActivity.class));
                break;
            case R.id.login:
                doLogin();
                //登录
                break;
        }
    }

    private void doLogin() {
        tvPhone = tv_phone.getText().toString().trim();
        if (TextUtils.isEmpty(tvPhone)) {
            toast("请输入您的手机号码");
            return;

        }
        if (tvPhone.length() != 11) {
            toast("手机号码不能少于或大于11位啊");
            return;

        }
        tvPwd = tv_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(tvPwd)) {
            toast("请输入登陆密码");
            return;

        }
        String encrypt = EncryptUtil.encrypt(tvPwd);
        Map<String, String> map = new HashMap<>();
        map.put("phone", tvPhone);
        map.put("pwd", encrypt);
        new HttpHelper().post(null, "/movieApi/user/v1/login", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                if ("0000".equals(loginBean.getStatus())) {
                    if (cb1.isChecked()) {
                        SharedPreferencesUtils.putString(context, "tv_phone", tvPhone);
                        SharedPreferencesUtils.putString(context, "tv_pwd", tvPwd);
                    } else {
                        SharedPreferencesUtils.putString(context, "tv_phone", "");
                        SharedPreferencesUtils.putString(context, "tv_pwd", "");
                    }
                    
                    SharedPreferencesUtils.putString(context, "tv_phone2", tvPhone);

                    SharedPreferencesUtils.putString(context, "headPic", loginBean.getResult().getUserInfo().getHeadPic());
                    SharedPreferencesUtils.putString(context, "nickName", loginBean.getResult().getUserInfo().getNickName());
                    SharedPreferencesUtils.putString(context, "sessionId", loginBean.getResult().getSessionId());
                    SharedPreferencesUtils.putInt(context, "userId", loginBean.getResult().getUserId());
                    SharedPreferencesUtils.putInt(context, "sex", loginBean.getResult().getUserInfo().getSex());
                    SharedPreferencesUtils.putString(context, "birthday", loginBean.getResult().getUserInfo().getBirthday());
                    toast(loginBean.getMessage() + "");
                    ((LoginActivity) context).finish();
                } else {
                    toast("手机号密码不正确");
                    return;
                }


            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //密码显示与隐藏
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.eye) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    eye.setImageResource(R.mipmap.log_icon_eye_default_xxhdpi);
                    tv_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码显示
                    tv_pwd.setSelection(tv_pwd.length());//但是密码显示后，文本光标会跑到开头去，重新在文本末获取一下光标
                    break;
                case MotionEvent.ACTION_UP:
                    eye.setImageResource(R.mipmap.log_icon_eye_default_xxhdpi);
                    tv_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码隐藏
                    tv_pwd.setSelection(tv_pwd.length());
                    break;
            }
        }
        return true;//这里要返回true，不然抬起事件会不响应，应该是事件分发机制的原因
    }

}
