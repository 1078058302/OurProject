package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.HashMap;
import java.util.Map;
/**
* @作者 THTF
* @创建日期 2018/12/05 14:13
*/
public class LoginActivityPresenter extends AppDelegate implements View.OnClickListener, View.OnTouchListener {
    private EditText mTv_phone, mTv_pwd;
    private String mTvPhone;
    private String mTvPwd;
    private CheckBox mCb1;
    private ImageView mEye;
    private IWXAPI api;
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((LoginActivity) context).apply();
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(APP_ID);
        mTv_phone = (EditText) get(R.id.tv_phone);
        mTv_pwd = (EditText) get(R.id.tv_pwd);
        mCb1 = (CheckBox) get(R.id.cb1);
        get(R.id.iv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        //获取数据
        String mTvPhone1 = SharedPreferencesUtils.getString(context, "tv_phone");
        String mTvPwd1 = SharedPreferencesUtils.getString(context, "tv_pwd");
        // 账号为空时，不选中
        if (TextUtils.isEmpty(mTvPhone1)) {
            mCb1.setChecked(false);
        } else {
            mCb1.setChecked(true);
        }
        //设置值
        mTv_phone.setText(mTvPhone1);
        mTv_pwd.setText(mTvPwd1);
        setClick(this, R.id.login, R.id.register, R.id.iv_login);
        mEye = (ImageView) get(R.id.eye);
        //图标监听事件
        mEye.setOnTouchListener(this);


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
                //登录
                doLogin();
                break;
            case R.id.iv_login:
                //微信登录
                doWxLogin();
                break;
        }
    }


    private void doLogin() {
        mTvPhone = mTv_phone.getText().toString().trim();
        if (TextUtils.isEmpty(mTvPhone)) {
            toast("请输入您的手机号码");
            return;

        }
        if (mTvPhone.length() != 11) {
            toast("手机号码不能少于或大于11位啊");
            return;

        }
        mTvPwd = mTv_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(mTvPwd)) {
            toast("请输入登陆密码");
            return;

        }
        String encrypt = EncryptUtil.encrypt(mTvPwd);
        Map<String, String> map = new HashMap<>();
        map.put("phone", mTvPhone);
        map.put("pwd", encrypt);
        new HttpHelper().post(null, "/movieApi/user/v1/login", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                if ("0000".equals(loginBean.getStatus())) {
                    //复选框选中保存账号和密码
                    if (mCb1.isChecked()) {
                        SharedPreferencesUtils.putString(context, "tv_phone", mTvPhone);
                        SharedPreferencesUtils.putString(context, "tv_pwd", mTvPwd);
                    } else {
                        SharedPreferencesUtils.putString(context, "tv_phone", "");
                        SharedPreferencesUtils.putString(context, "tv_pwd", "");
                    }
                    SharedPreferencesUtils.putString(context, "tv_phone2", mTvPhone);
                    SharedPreferencesUtils.putString(context, "headPic", loginBean.getResult().getUserInfo().getHeadPic());
                    SharedPreferencesUtils.putString(context, "nickName", loginBean.getResult().getUserInfo().getNickName());
                    SharedPreferencesUtils.putString(context, "sessionId", loginBean.getResult().getSessionId());
                    SharedPreferencesUtils.putInt(context, "userId", loginBean.getResult().getUserId());
                    SharedPreferencesUtils.putInt(context, "sex", loginBean.getResult().getUserInfo().getSex());
                    long birthday = loginBean.getResult().getUserInfo().getBirthday();
                    String format = DateUtils.format(birthday, "yyyy-MM-dd");
                    SharedPreferencesUtils.putString(context, "birthday", format);
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
                    mEye.setImageResource(R.mipmap.log_icon_eye_default_xxhdpi);
                    mTv_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码显示
                    mTv_pwd.setSelection(mTv_pwd.length());//但是密码显示后，文本光标会跑到开头去，重新在文本末获取一下光标
                    break;
                case MotionEvent.ACTION_UP:
                    mEye.setImageResource(R.mipmap.log_icon_eye_default_xxhdpi);
                    mTv_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码隐藏
                    mTv_pwd.setSelection(mTv_pwd.length());
                    break;
            }
        }
        return true;//这里要返回true，不然抬起事件会不响应，应该是事件分发机制的原因
    }

    //微信登录
    private void doWxLogin() {
        toast("sss");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
//        req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
        req.state = "wechat_sdk_微信登录";
        api.sendReq(req);
    }
}
