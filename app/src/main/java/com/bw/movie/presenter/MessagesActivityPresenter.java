package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class MessagesActivityPresenter extends AppDelegate {
    private TextView tv_unread;
    private XRecyclerView xRecyclerView3;

    @Override
    public int getLayoutId() {
        return R.layout.activity_messages;
    }

    @Override
    public void initData() {
        super.initData();
        tv_unread = (TextView) get(R.id.tv_unread);
        xRecyclerView3 = (XRecyclerView) get(R.id.xRecyclerView3);
        doGet();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView3.setLayoutManager(linearLayoutManager);

    }

    private void doGet() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "5");
        Map<String, String> mapHead = new HashMap<>();
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/tool/v1/verify/findAllSysMsgList", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
            }

            @Override
            public void fail(String error) {

            }
        });

    }

    public void setContext(Context context) {
        this.context = context;
    }
}
