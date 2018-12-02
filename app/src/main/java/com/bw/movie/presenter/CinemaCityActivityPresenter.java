package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaCityActivity;
import com.bw.movie.adapter.CinemaDetailsAdapter;
import com.bw.movie.mvp.model.CinemaDetailsBean;
import com.bw.movie.mvp.model.MovieDescBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaCityActivityPresenter extends AppDelegate {

    private int movice_ids;
    private MovieDescBean.ResultBean result = null;
    private TextView film_city_name;
    private TextView film_city;
    private SimpleDraweeView img2;
    private TextView film_name_s;
    private TextView type_film;
    private TextView arter;
    private TextView time_film;
    private String address;
    private String name;
    private String filmname;
    private int cinemaid;
    private XRecyclerView film_recycle;
    private CinemaDetailsAdapter cinemaDetailsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cinema_city;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((CinemaCityActivity) context)
                .apply();
        film_city_name = (TextView) get(R.id.film_city_name);
        film_city = (TextView) get(R.id.film_city);
        img2 = (SimpleDraweeView) get(R.id.img2);
        film_name_s = (TextView) get(R.id.film_name_s);
        type_film = (TextView) get(R.id.type_film);
        arter = (TextView) get(R.id.arter);
        time_film = (TextView) get(R.id.time_film);
        film_recycle = (XRecyclerView)get(R.id.film_recycle);

        Intent intent = ((CinemaCityActivity) context).getIntent();
        address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");
        String logo = intent.getStringExtra("logo");
        cinemaid = intent.getIntExtra("cinemaid", 0);
        filmname = SharedPreferencesUtils.getString(context, "filmname");
        movice_ids = SharedPreferencesUtils.getInt(context, "movice_ids");
        Log.i("大大雙", "initData: "+cinemaid+"----"+movice_ids);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        film_recycle.setLayoutManager(staggeredGridLayoutManager);
        cinemaDetailsAdapter = new CinemaDetailsAdapter();
        cinemaDetailsAdapter.setContext(context);
        film_recycle.setAdapter(cinemaDetailsAdapter);
        film_recycle.setPullRefreshEnabled(true);
        film_recycle.setLoadingMoreEnabled(false);
        film_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttp();
            }

            @Override
            public void onLoadMore() {

            }
        });

        doMovieDescHttp();
        doHttp();


    }

    private void doHttp() {
        Map map=new HashMap<>();
        map.put("cinemasId",cinemaid);
        map.put("movieId",movice_ids);
        new HttpHelper().get("movieApi/movie/v1/findMovieScheduleList",map).result(new HttpListener() {
            @Override
            public void success(String data) {
                CinemaDetailsBean cinemaDetailsBean = new Gson().fromJson(data, CinemaDetailsBean.class);
                List<CinemaDetailsBean.ResultBean> result = cinemaDetailsBean.getResult();
                if (result.size()==0) {
                    doNull();
                    return;
                }
                cinemaDetailsAdapter.setList(result);
                film_recycle.refreshComplete();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doNull() {
        new HttpHelper().get("movieApi/movie/v1/findMovieScheduleList?cinemasId=2&movieId=3",null).result(new HttpListener() {
            @Override
            public void success(String data) {
                CinemaDetailsBean cinemaDetailsBean = new Gson().fromJson(data, CinemaDetailsBean.class);
                cinemaDetailsAdapter.setList(cinemaDetailsBean.getResult());
                film_recycle.refreshComplete();
            }
            @Override
            public void fail(String error) {

            }
        });
    }


    private void doMovieDescHttp() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movice_ids + "");
        new HttpHelper().get("movieApi/movie/v1/findMoviesDetail", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                MovieDescBean movieDescBean = new Gson().fromJson(data, MovieDescBean.class);
                result = movieDescBean.getResult();
                img2.setImageURI(result.getImageUrl());
                film_city_name.setText(result.getName());
                film_city.setText(address);
                film_name_s.setText(filmname);
                arter.setText("导演:"+result.getDirector());
                time_film.setText("时长:"+result.getDuration());
                type_film.setText("类型:"+result.getMovieTypes());

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
