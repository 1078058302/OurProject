package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.FilmCinemaActivity;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.adapter.FilmCinemaAdapter;
import com.bw.movie.mvp.model.FilmCinemaBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmCinemaActivityPresenter extends AppDelegate {

    private FilmCinemaAdapter filmCinemaAdapter;
    private XRecyclerView film_cinema_recycle;
    private TextView film_name;
    private String name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_film_cinema;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((FilmCinemaActivity) context)
                .apply();
        film_name = (TextView)get(R.id.film_name);
        film_cinema_recycle = (XRecyclerView) get(R.id.film_cinema_recycle);
        filmCinemaAdapter = new FilmCinemaAdapter(context);
        name = ((FilmCinemaActivity) context).getIntent().getStringExtra("name");
        if (name != null) {
            SharedPreferencesUtils.putString(context,"filmname",name);
        }
        film_name.setText(name);


        film_cinema_recycle.setPullRefreshEnabled(true);
        film_cinema_recycle.setLoadingMoreEnabled(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        film_cinema_recycle.setLayoutManager(staggeredGridLayoutManager);
        film_cinema_recycle.setAdapter(filmCinemaAdapter);

        film_cinema_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                doHttp();
            }

            @Override
            public void onLoadMore() {
                doHttp();
            }
        });
        doHttp();

    }

    private void doHttp() {

        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "9");
        new HttpHelper().get("/movieApi/cinema/v1/findAllCinemas", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                FilmCinemaBean filmCinemaBean = new Gson().fromJson(data, FilmCinemaBean.class);
                List<FilmCinemaBean.ResultBean> result = filmCinemaBean.getResult();
                filmCinemaAdapter.setList(result);
                film_cinema_recycle.refreshComplete();
                film_cinema_recycle.loadMoreComplete();

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
