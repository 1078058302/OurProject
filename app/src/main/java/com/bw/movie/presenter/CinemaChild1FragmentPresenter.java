package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaChild1Adapter;
import com.bw.movie.adapter.CinemaChildAdapter;
import com.bw.movie.mvp.model.NearByBean;
import com.bw.movie.mvp.model.RecommendBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaChild1FragmentPresenter extends AppDelegate {
    private XRecyclerView xRecyclerView;
    private CinemaChild1Adapter adapter;
    private String sessionId;
    private int userId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cinema_child;
    }

    @Override
    public void initData() {
        super.initData();
        sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        userId = SharedPreferencesUtils.getInt(context, "userId");
        xRecyclerView = get(R.id.show_cinema);
        adapter = new CinemaChild1Adapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setAdapter(adapter);
        doHttp();
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttp();
            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
    }

    private void doHttp() {
        Map mapHead = new HashMap();
        Map map = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        map.put("page", 1);
        map.put("count", 21);
        map.put("longitude", "116.30551391385724");
        map.put("latitude", "40.04571807462411");
        new HttpHelper().getHead("/movieApi/cinema/v1/findRecommendCinemas", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("<")) {
                    doHttp();
                }
                NearByBean nearByBean = new Gson().fromJson(data, NearByBean.class);
                List<NearByBean.ResultBean.NearbyCinemaListBean> nearbyCinemaList = nearByBean.getResult().getNearbyCinemaList();
                List<NearByBean.ResultBean.FollowCinemasBean> followCinemas = nearByBean.getResult().getFollowCinemas();
                adapter.setList(nearbyCinemaList, followCinemas);
                adapter.setContext(context);
                xRecyclerView.refreshComplete();

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
