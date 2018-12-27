package com.bw.movie.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.text.TextUtils;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;


import com.bw.movie.R;
import com.bw.movie.activity.AttentionActivity;
import com.bw.movie.activity.FeedBackActivity;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.MainActivity;


import com.bw.movie.activity.MessagesActivity;
import com.bw.movie.activity.TicketRecordActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.model.VersionUpdateBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MineFragmentPresenter extends AppDelegate implements View.OnClickListener {
    private SimpleDraweeView mSd;
    private TextView mTv_Name;
    private String mNickName,mHeadPath,mHeadPic;
    private SimpleDraweeView sd;
    private TextView tv_name;
    private String nickName;
    private String headPath;
    private String headPic;
    private View inflate;
    private DatePicker date;
    private AlertDialog.Builder builder;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int userid;
    private String sessionid;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();
        mSd = (SimpleDraweeView) get(R.id.sd);
        mTv_Name = (TextView) get(R.id.tv_name);

        userid = SharedPreferencesUtils.getInt(context, "userId");


        sd = (SimpleDraweeView) get(R.id.sd);
        tv_name = (TextView) get(R.id.tv_name);


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        get(R.id.come).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionid = SharedPreferencesUtils.getString(context, "sessionId");
                if (TextUtils.isEmpty(sessionid)) {
                    toast("您还没有登录");
                    return;
                } else {
                    inflate = View.inflate(context, R.layout.activity_sign, null);
                    date = (DatePicker) inflate.findViewById(R.id.date);
                    new AlertDialog.Builder(context).setView(inflate).setIcon(R.mipmap.logo).create().show();
                    date.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            doSign();
                        }
                    });
                }

            }
        });


        setClick(this, R.id.sd, R.id.tv_name, R.id.rl1);
        setClick(this, R.id.sd, R.id.tv_name, R.id.rl1, R.id.rl2, R.id.rl4, R.id.rl5, R.id.iv_messages, R.id.rl3);
    }

    private void doSign() {
        Map map = new HashMap<>();
        map.put("userId", userid);
        map.put("sessionId", sessionid);
        new HttpHelper().getHead("/movieApi/user/v1/verify/userSignIn", null, map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("签到成功")) {
                    toast("签到成功");
                } else {
                    toast("今天已签到");
                }
            }

            @Override
            public void fail(String error) {

            }
        });
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
                nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (TextUtils.isEmpty(nickName)) {
                    toast("您还未登录，请先去登录");
                } else {
                    context.startActivity(new Intent(context, UserInfoActivity.class));
                }


                break;
            case R.id.rl2:
                //我的关注
                nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (TextUtils.isEmpty(nickName)) {
                    toast("您还未登录，请先去登录");
                } else {
                    context.startActivity(new Intent(context, AttentionActivity.class));
                }

                break;
            case R.id.rl3:
                //购票记录
                nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (TextUtils.isEmpty(nickName)) {
                    toast("您还未登录，请先去登录");
                } else {
                    context.startActivity(new Intent(context, TicketRecordActivity.class));
                }


                break;
            case R.id.rl4:
                //意见反馈
                nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (TextUtils.isEmpty(nickName)) {
                    toast("您还未登录，请先去登录");
                } else {
                    context.startActivity(new Intent(context, FeedBackActivity.class));
                }

                break;
            case R.id.rl5:
                //最新版本
                nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (TextUtils.isEmpty(nickName)) {
                    toast("您还未登录，请先去登录");
                } else {
                    doVersions();
                }

                break;
            case R.id.iv_messages:
                //系统消息
                nickName = SharedPreferencesUtils.getString(context, "nickName");
                if (TextUtils.isEmpty(nickName)) {
                    toast("您还未登录，请先去登录");
                } else {
                    context.startActivity(new Intent(context, MessagesActivity.class));
                }

                break;

        }
    }


    public void onResume() {
        mNickName = SharedPreferencesUtils.getString(context, "nickName");
        if (!TextUtils.isEmpty(mNickName)) {
            mTv_Name.setText(mNickName);
        } else {
            mTv_Name.setText("未登录");

        }
        mHeadPic = SharedPreferencesUtils.getString(context, "headPic");
        mHeadPath = SharedPreferencesUtils.getString(context, "headPath");
        if (!TextUtils.isEmpty(mHeadPath)) {
            mSd.setImageURI(mHeadPath);
        } else {
            if (!TextUtils.isEmpty(mHeadPic)) {
                mSd.setImageURI(mHeadPic);
            } else {
                mSd.setImageResource(R.mipmap.logo);
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
                    Gson gson = new Gson();
                    VersionUpdateBean versionUpdateBean = gson.fromJson(data, VersionUpdateBean.class);
                    int flag = versionUpdateBean.getFlag();
                    if (flag == 1) {
                        final String downloadUrl = versionUpdateBean.getDownloadUrl();
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("版本更新");
                        alertDialog.setMessage("当前有新版本，是否进行更新");
                        alertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri = Uri.parse(downloadUrl.trim());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                ((MainActivity) context).startActivity(intent);
                            }
                        });
                        alertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        //显示
                        alertDialog.show();
                    } else if (flag == 2) {
                        toast("当前版本已是最新版本,无需更新");
                    } else {
                        toast("版本更新失败。");
                    }
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
