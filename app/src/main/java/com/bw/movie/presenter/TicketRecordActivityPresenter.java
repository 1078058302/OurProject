package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.bw.movie.R;
import com.bw.movie.activity.TicketRecordActivity;
import com.bw.movie.adapter.TicketRecordAdapter;
import com.bw.movie.mvp.model.TicketRecordBean;
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

public class TicketRecordActivityPresenter extends AppDelegate {
    private XRecyclerView xRecyclerView4;
    private List<TicketRecordBean.ResultBean> result = new ArrayList<>();
    private TicketRecordAdapter ticketRecordAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ticketrecord;
    }

    @Override
    public void initData() {
        super.initData();
        xRecyclerView4 = (XRecyclerView) get(R.id.xRecyclerView4);
        doGet();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView4.setLayoutManager(linearLayoutManager);
        ticketRecordAdapter = new TicketRecordAdapter(context);
        xRecyclerView4.setAdapter(ticketRecordAdapter);

    }

    private void doGet() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> mapHead = new HashMap<>();
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "5");
        new HttpHelper().getHead("/movieApi/user/v1/verify/findUserBuyTicketRecordList", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                TicketRecordBean ticketRecordBean = gson.fromJson(data, TicketRecordBean.class);
                if ("0000".equals(ticketRecordBean.getStatus())) {
                    result = ticketRecordBean.getResult();
                        ticketRecordAdapter.setList(result);
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
}
