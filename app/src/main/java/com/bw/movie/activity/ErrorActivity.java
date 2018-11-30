package com.bw.movie.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.bw.movie.R;
import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.ErrorActivityPresenter;

public class ErrorActivity extends BaseActivityPresenter<ErrorActivityPresenter> {


    @Override
    public Class getClassDelegate() {
        return ErrorActivityPresenter.class;
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
                        ErrorActivity.this.finish();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();

    }

}
