package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.adapter.CineamsAdapter;
import com.bw.movie.adapter.FilmAdapter;
import com.bw.movie.mvp.model.GuanZhuBean;
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

public class FilmFragmentPresenter extends AppDelegate {
    private XRecyclerView mXRecyclerView1;
    private List<GuanZhuBean.ResultBean> result = new ArrayList<>();
    private FilmAdapter mFilmAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_film;
    }

    @Override
    public void initData() {
        super.initData();
        mXRecyclerView1 = (XRecyclerView) get(R.id.xRecyclerView1);
        doHttp();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView1.setLayoutManager(linearLayoutManager);
        mFilmAdapter = new FilmAdapter(context);
        mXRecyclerView1.setAdapter(mFilmAdapter);
        //接口回调
        mFilmAdapter.setOnItemClickListener(new CineamsAdapter.OnItemClickListener() {
            @Override
            public void setItem() {
                doHttp();
            }
        });
        mXRecyclerView1.setPullRefreshEnabled(true);
        mXRecyclerView1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttp();
            }

            @Override
            public void onLoadMore() {
                mXRecyclerView1.loadMoreComplete();
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
        new HttpHelper().getHead("/movieApi/movie/v1/verify/findMoviePageList", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                GuanZhuBean guanZhuBean = gson.fromJson(data, GuanZhuBean.class);
                if ("0000".equals(guanZhuBean.getStatus())) {
                    toast("查询成功");
                    result = guanZhuBean.getResult();
                    mFilmAdapter.setList(result);
                    mXRecyclerView1.refreshComplete();
                }

            }

            @Override
            public void fail(String error) {

            }
        });

    }

    public void setContxt(Context contxt) {
        this.context = contxt;
    }
}
