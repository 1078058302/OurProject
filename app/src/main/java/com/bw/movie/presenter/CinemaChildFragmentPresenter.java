package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaChildAdapter;
import com.bw.movie.mvp.model.RecommendBean;
import com.bw.movie.mvp.model.ShowBean;
import com.bw.movie.mvp.model.TrueRecommendBean;
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

public class CinemaChildFragmentPresenter extends AppDelegate {

    private XRecyclerView xRecyclerView;
    private CinemaChildAdapter adapter;
    private String sessionId;
    private int userId;
    private List<ShowBean> showBeans = new ArrayList<>();

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
        adapter = new CinemaChildAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        if (TextUtils.isEmpty(sessionId)) {
            xRecyclerView.setAdapter(adapter);
            doHttp();
        } else {
            doReconHttp();
        }
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (TextUtils.isEmpty(sessionId)) {
                    doHttp();
                } else {
                    doReconHttp();
                }
            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
    }

    private void doReconHttp() {
        //http://172.17.8.100/movieApi/cinema/v1/findRecommendCinemas
        Log.i("CinemaChildFragment", userId + "……" + sessionId);
        Map map = new HashMap();
        map.put("page", 1);
        map.put("count", 60);
        map.put("longitude", "116.30551391385724");
        map.put("latitude", "40.04571807462411");
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/cinema/v1/findRecommendCinemas", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
//                Log.i("CinemaChildFragment", data);
                TrueRecommendBean trueRecommendBean = new Gson().fromJson(data, TrueRecommendBean.class);
                List<TrueRecommendBean.ResultBean.FollowCinemasBean> followCinemas = trueRecommendBean.getResult().getFollowCinemas();
                List<TrueRecommendBean.ResultBean.NearbyCinemaListBean> nearbyCinemaList = trueRecommendBean.getResult().getNearbyCinemaList();
                ShowBean showBean = null;
                if (!followCinemas.isEmpty()) {
                    for (int i = 0; i < followCinemas.size(); i++) {
                        showBean = new ShowBean();
                        showBean.setAddress(followCinemas.get(i).getAddress());
                        showBean.setCommentTotal(followCinemas.get(i).getCommentTotal());
                        showBean.setDistance(followCinemas.get(i).getDistance());
                        showBean.setFollowCinema(followCinemas.get(i).isFollowCinema());
                        showBean.setId(followCinemas.get(i).getId());
                        showBean.setLogo(followCinemas.get(i).getLogo());
                        showBean.setName(followCinemas.get(i).getName());
                        showBeans.add(showBean);
                        Log.i("CinemaChildFragment", showBeans.get(i) + "");
                    }
                    for (int i = 0; i < nearbyCinemaList.size(); i++) {
                        showBean = new ShowBean();
                        int distance = nearbyCinemaList.get(i).getDistance();
                        if (distance < 5000) {
                            showBean.setAddress(nearbyCinemaList.get(i).getAddress());
                            showBean.setCommentTotal(nearbyCinemaList.get(i).getCommentTotal());
                            showBean.setDistance(nearbyCinemaList.get(i).getDistance());
                            showBean.setFollowCinema(nearbyCinemaList.get(i).isFollowCinema());
                            showBean.setId(nearbyCinemaList.get(i).getId());
                            showBean.setLogo(nearbyCinemaList.get(i).getLogo());
                            showBean.setName(nearbyCinemaList.get(i).getName());
                            showBeans.add(showBean);
                        }
                    }

                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //123
    private void doHttp() {
        Map map = new HashMap();
        map.put("page", 1);
        map.put("count", 21);
        new HttpHelper().get("/movieApi/cinema/v1/findAllCinemas", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("<")) {
                    doHttp();
                }
                RecommendBean recommendBean = new Gson().fromJson(data, RecommendBean.class);
                List<RecommendBean.ResultBean> result = recommendBean.getResult();
                adapter.setList(result);
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

    public void onResume() {
//        Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        if (TextUtils.isEmpty(sessionId)) {
            doHttp();
            Toast.makeText(context, "doHttp", Toast.LENGTH_SHORT).show();
        } else {
            doReconHttp();
            Toast.makeText(context, "doReconHttp", Toast.LENGTH_SHORT).show();
        }
    }
}
