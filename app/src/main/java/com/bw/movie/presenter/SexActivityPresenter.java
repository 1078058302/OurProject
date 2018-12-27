package com.bw.movie.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.SexActivity;
import com.bw.movie.mvp.model.NiChengBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SexActivityPresenter extends AppDelegate implements View.OnClickListener {
    private String mNickName;
    private int sex;
    private String email;
    private ImageView duiHao1, duiHao2;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sex;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((SexActivity) context).apply();
        duiHao1 = (ImageView) get(R.id.duiHao1);
        duiHao2 = (ImageView) get(R.id.duiHao2);
        setClick(this, R.id.iv_fanhui2, R.id.rl_check1, R.id.rl_check2);
        mNickName = SharedPreferencesUtils.getString(context, "nickName");
        sex = SharedPreferencesUtils.getInt(context, "sex");
        email = SharedPreferencesUtils.getString(context, "email");
        if(sex==1){
            duiHao1.setVisibility(View.VISIBLE);
        }else {
            duiHao2.setVisibility(View.VISIBLE);
        }

    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui2:
                ((SexActivity) context).finish();
                break;
            case R.id.rl_check1:
                if(duiHao1.getVisibility()==View.VISIBLE){
                    duiHao1.setVisibility(View.GONE);
                }else{
                    duiHao1.setVisibility(View.VISIBLE);
                    duiHao2.setVisibility(View.GONE);
                }
                sex=1;
                doGet();
                break;
            case R.id.rl_check2:
                if(duiHao2.getVisibility()==View.VISIBLE){
                    duiHao2.setVisibility(View.GONE);
                }else{
                    duiHao2.setVisibility(View.VISIBLE);
                    duiHao1.setVisibility(View.GONE);

                }
                sex=2;
                doGet();
                break;
        }

    }

    private void doGet() {

        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        m.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("nickName", mNickName);
        map.put("sex", sex + "");
        map.put("email", email);
        new HttpHelper().post(m, "/movieApi/user/v1/verify/modifyUserInfo", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                NiChengBean niChengBean = gson.fromJson(data, NiChengBean.class);
                if ("0000".equals(niChengBean.getStatus())) {
                    SharedPreferencesUtils.putInt(context, "sex", niChengBean.getResult().getSex());
                    toast("修改成功"+niChengBean.getResult().getSex()+"");
                    ((SexActivity) context).finish();

                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

}
