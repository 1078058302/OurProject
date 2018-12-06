package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.FeedBackSuccessActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

public class FeedBackActivityPresenter extends AppDelegate implements View.OnClickListener {
    private EditText et_content = null;
    private TextView text_count = null;
    private static final int MAX_COUNT = 200;//最大字数

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initData() {
        super.initData();
        et_content = (EditText) get(R.id.et_content);
        text_count = (TextView) get(R.id.text_count);

        //edittext监听事件
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //设置值
                text_count.setText("剩余字数:" + (MAX_COUNT - editable.length()));
            }
        });
        setClick(this, R.id.bt_submit);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                //提交
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        String etContent = et_content.getText().toString().trim();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        m.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("content", etContent);
        new HttpHelper().post(m, "/movieApi/tool/v1/verify/recordFeedBack", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("反馈成功")) {
                    toast("反馈成功");
                    context.startActivity(new Intent(context, FeedBackSuccessActivity.class));
                } else {
                    toast("反馈失败");
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
