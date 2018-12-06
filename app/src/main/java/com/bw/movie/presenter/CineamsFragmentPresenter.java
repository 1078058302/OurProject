package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.bw.movie.R;
import com.bw.movie.adapter.CineamsAdapter;
import com.bw.movie.mvp.model.GuanZhu2Bean;
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

public class CineamsFragmentPresenter extends AppDelegate {
    private XRecyclerView xRecyclerView2;
    private CineamsAdapter cineamsAdapter;
    private List<GuanZhu2Bean.ResultBean> result = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cineams;
    }

    @Override
    public void initData() {
        super.initData();
        xRecyclerView2 = (XRecyclerView) get(R.id.xRecyclerView2);
        doHttp();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView2.setLayoutManager(linearLayoutManager);
        cineamsAdapter = new CineamsAdapter(context);
        xRecyclerView2.setAdapter(cineamsAdapter);
        xRecyclerView2.setPullRefreshEnabled(true);
        xRecyclerView2.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttp();
            }

            @Override
            public void onLoadMore() {
                xRecyclerView2.loadMoreComplete();
            }
        });
        //接口回调
        cineamsAdapter.setOnItemClickListener(new CineamsAdapter.OnItemClickListener() {
            @Override
            public void setItem() {
                doHttp();
            }
        });
    }

    private void doHttp() {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map<String, String> mapHead = new HashMap<>();
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "10");
        new HttpHelper().getHead("/movieApi/cinema/v1/verify/findCinemaPageList", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                GuanZhu2Bean guanZhu2Bean = gson.fromJson(data, GuanZhu2Bean.class);
                if ("0000".equals(guanZhu2Bean.getStatus())) {
                    result = guanZhu2Bean.getResult();
                    cineamsAdapter.setList(result);
                    xRecyclerView2.refreshComplete();
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
