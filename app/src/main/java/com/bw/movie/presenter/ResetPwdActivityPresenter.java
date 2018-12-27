package com.bw.movie.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bw.movie.R;
import com.bw.movie.activity.ResetPwdActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.service.EncryptUtil;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;

import java.util.HashMap;
import java.util.Map;

public class ResetPwdActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText mEt_oldPwd, mEt_newPwd, mEt_newPwd2;


    @Override
    public int getLayoutId() {
        return R.layout.activity_resetpwd;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((ResetPwdActivity) context).apply();
        setClick(this, R.id.iv_fanhui4, R.id.tv_confim2);
        mEt_oldPwd = (EditText) get(R.id.et_oldPwd);
        mEt_newPwd = (EditText) get(R.id.et_newPwd);
        mEt_newPwd2 = (EditText) get(R.id.et_newPwd2);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui4:
                //返回
                ((ResetPwdActivity) context).finish();
                break;
            case R.id.tv_confim2:
                //确定
                doPost();
                break;
        }
    }

    private void doPost() {
        String OldPwd = mEt_oldPwd.getText().toString().trim();
        if (TextUtils.isEmpty(OldPwd)) {
            toast("请输入密码");
            return;
        }
        String NewPwd = mEt_newPwd.getText().toString().trim();
        if (TextUtils.isEmpty(NewPwd)) {
            toast("请输入新密码");
            return;
        }
        String NewPwd2 = mEt_newPwd2.getText().toString().trim();
        if (TextUtils.isEmpty(NewPwd)) {
            toast("请输入重复密码");
            return;
        }
        if (!NewPwd2.equals(NewPwd)) {
            toast("请保持密码一致");
            return;
        }
        //老密码加密
        String decrypt = EncryptUtil.encrypt(OldPwd);
        Log.i("444", decrypt);
        //新密码加密
        String decrypt1 = EncryptUtil.encrypt(NewPwd);
        Log.i("杀杀杀", decrypt1);
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        m.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("oldPwd", decrypt);
        map.put("newPwd", decrypt1);
        map.put("newPwd2", decrypt1);
        new HttpHelper().post(m, "/movieApi/user/v1/verify/modifyUserPwd", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("密码修改成功")) {
                    toast("密码修改成功");
                    ((ResetPwdActivity) context).finish();
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }
}
