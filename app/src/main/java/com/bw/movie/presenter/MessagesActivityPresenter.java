package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.MessagesAdapter;
import com.bw.movie.mvp.model.MessagesBean;
import com.bw.movie.mvp.model.UnreadMessageBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesActivityPresenter extends AppDelegate {
    private TextView tv_unread;
    private XRecyclerView xRecyclerView3;
    private List<MessagesBean.ResultBean> result = new ArrayList<>();
    private MessagesAdapter messagesAdapter;

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
        messagesAdapter = new MessagesAdapter(context);
        xRecyclerView3.setAdapter(messagesAdapter);
        xRecyclerView3.setPullRefreshEnabled(true);
        xRecyclerView3.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doGet();
            }

            @Override
            public void onLoadMore() {
                xRecyclerView3.loadMoreComplete();
            }
        });
        doGets();
        //接口回调
        messagesAdapter.setOnRefreshClickListener(new MessagesAdapter.onRefreshClickListener() {
            @Override
            public void setRefresh() {
                doGet();
            }
        });

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
                Gson gson = new Gson();
                MessagesBean messagesBean = gson.fromJson(data, MessagesBean.class);
                if ("0000".contains(messagesBean.getStatus())) {
                    result = messagesBean.getResult();
                    messagesAdapter.setList(result);
                    xRecyclerView3.refreshComplete();
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }

    private void doGets() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> mapHead = new HashMap<>();
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/tool/v1/verify/findUnreadMessageCount", null, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                UnreadMessageBean unreadMessageBean = gson.fromJson(data, UnreadMessageBean.class);
                if ("0000".equals(unreadMessageBean.getStatus())) {
                    int count = unreadMessageBean.getCount();
                    if (count == 1) {
                        tv_unread.setText((count + "条未读"));
                    } else {
                        tv_unread.setText((count + "条未读"));
                    }

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

    //聚焦
    public void onResume() {
        doGets();
    }
}
