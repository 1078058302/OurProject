package com.bw.movie.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText mEt_NiCheng, mEt_Sex, mEt_Date, mEt_Phone, mEt_Email, mEt_Pwd, mEt_Pwd2;
    private int mDay;
    private int mMonth;
    private int mYear;
    private String[] sexArry = new String[]{"男", "女"};// 性别选择
    private int index = -1;//全局变量
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            }

            mEt_Date.setText(days);

        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((RegisterActivity) context).apply();

        mEt_NiCheng = (EditText) get(R.id.et_nicheng);
        mEt_Sex = (EditText) get(R.id.et_sex);
        mEt_Date = (EditText) get(R.id.et_date);
        mEt_Phone = (EditText) get(R.id.et_phone);
        mEt_Email = (EditText) get(R.id.et_email);
        mEt_Pwd = (EditText) get(R.id.et_pwd);
        mEt_Pwd2 = (EditText) get(R.id.et_pwd2);
        setClick(this, R.id.bt_register);
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        //日期选择器
        mEt_Date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    return true;
                }
                return false;
            }
        });
        mEt_Sex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showSexChooseDialog();
                    return true;
                }
                return false;
            }
        });
    }

    //性别选择器
    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setSingleChoiceItems(sexArry, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                index = i;
                mEt_Sex.setText(sexArry[i]);
                dialogInterface.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();
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

        String mEtNicheng = mEt_NiCheng.getText().toString().trim();
        if (TextUtils.isEmpty(mEtNicheng)) {
            toast("请输入昵称");
            return;
        }
        String mEtSex = mEt_Sex.getText().toString().trim();
        if (TextUtils.isEmpty(mEtSex)) {
            toast("请输入性别");
            return;
        }
        String mEtDate = mEt_Date.getText().toString().trim();

        String mEtPhone = mEt_Phone.getText().toString();
        if (TextUtils.isEmpty(mEtPhone)) {
            toast("手机号不能为空");
            return;
        }
        final String mEtEmail = mEt_Email.getText().toString().trim();
        if (TextUtils.isEmpty(mEtEmail)) {
            toast("请输入邮箱");
            return;
        }

        String mEtPwd = mEt_Pwd.getText().toString().trim();
        if (TextUtils.isEmpty(mEtPwd)) {
            toast("请输入登陆密码");
            return;
        }

        String mEtPwd2 = mEt_Pwd2.getText().toString().trim();
        if (!mEtPwd2.equals(mEtPwd)) {
            toast("请保持密码一致");
            return;
        }
        String encrypt = EncryptUtil.encrypt(mEtPwd);

        Map<String, String> map = new HashMap<>();
        map.put("nickName", mEtNicheng);
        if (mEtNicheng.equals("男")) {
            map.put("sex", "1");
        } else {
            map.put("sex", "2");
        }
        map.put("birthday", mEtDate);
        map.put("phone", mEtPhone);
        map.put("email", mEtEmail);
        map.put("pwd", encrypt);
        map.put("pwd2", encrypt);
        new HttpHelper().post(null, "/movieApi/user/v1/registerUser", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(data, RegisterBean.class);
                if ("0000".equals(registerBean.getStatus())) {
                    toast("注册成功");
                    SharedPreferencesUtils.putString(context, "email", mEtEmail);
                    context.startActivity(new Intent(context, LoginActivity.class));
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

