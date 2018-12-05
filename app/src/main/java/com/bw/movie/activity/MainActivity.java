package com.bw.movie.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.bw.movie.R;
import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.MainActivityPresenter;
import com.bw.movie.utils.SharedPreferencesUtils;
//主Activity


public class MainActivity extends BaseActivityPresenter<MainActivityPresenter> {




    @Override
    public Class<MainActivityPresenter> getClassDelegate() {
        return MainActivityPresenter.class;
    }


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);

        delegate.setContext(context);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出维度影院吗？")
                .setIcon(R.mipmap.logo)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                       MainActivity.this.finish();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否点击返回键
       boolean isback= SharedPreferencesUtils.getBoolean(this,"isback");
       if(isback){
           //如果是false的话就不让他finish()
           SharedPreferencesUtils.putBoolean(this,"isback",false);
           finish();
       }

    }
}
