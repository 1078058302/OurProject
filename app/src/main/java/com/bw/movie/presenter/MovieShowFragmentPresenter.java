package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bw.movie.R;
import com.bw.movie.activity.MovieShowActivity;
import com.bw.movie.adapter.HotMovieAdapter;
import com.bw.movie.adapter.MovieingAdapter;
import com.bw.movie.adapter.MyPagerAdapter;
import com.bw.movie.adapter.NextMovieAdapter;
import com.bw.movie.adapter.RotationPageTransformer;
import com.bw.movie.model.BannerBean;
import com.bw.movie.mvp.model.HotMovieBean;
import com.bw.movie.mvp.model.MovieingBean;
import com.bw.movie.mvp.model.NextMovieBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieShowFragmentPresenter extends AppDelegate implements View.OnClickListener {

    private RecyclerView hotRecycle;
    HotMovieAdapter hotMovieAdapter = new HotMovieAdapter();
    MovieingAdapter movieingAdapter = new MovieingAdapter();
    NextMovieAdapter nextMovieAdapter = new NextMovieAdapter();
    private RecyclerView nextRecycle;
    private RecyclerView movieingRecycle;
    private List<String> images = new ArrayList<>();
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private ProgressBar progress_page;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initData() {
        super.initData();
        mViewPager = (ViewPager) get(R.id.vp_movie);
        /*   progress_page = (ProgressBar) get(R.id.progress_page);*/
        ImageView nextMovie = get(R.id.nextMovie_image_moviefragment);
        ImageView hotMovie = get(R.id.hotMovie_image_moviefragment);
        ImageView Movieing = get(R.id.Movieing_image_moviefragment);
        hotRecycle = get(R.id.hotMovie_recycle);
        movieingRecycle = get(R.id.movieing_recycle);
        nextRecycle = get(R.id.nextmovie_recycle);
        setClick(this, R.id.nextMovie_image_moviefragment, R.id.hotMovie_image_moviefragment, R.id.Movieing_image_moviefragment);
        //顶部图片轮播
        doHttp();
        //设置正在热映的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotRecycle.setLayoutManager(linearLayoutManager);
        hotRecycle.setAdapter(hotMovieAdapter);
        doHothttp();
        //正在上映的布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        movieingRecycle.setLayoutManager(linearLayoutManager1);
        movieingRecycle.setAdapter(movieingAdapter);
        doMovieingHttp();
        //即将上映的布局管理器
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        nextRecycle.setLayoutManager(linearLayoutManager2);
        nextRecycle.setAdapter(nextMovieAdapter);
        doNextHttp();
    }

    //顶部图片轮播
    private void doHttp() {
        new HttpHelper().get("/movieApi/movie/v1/findHotMovieList?page=1&count=6", null).result(new HttpListener() {
            @Override
            public void success(String data) {
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                List<BannerBean.ResultBean> result = bannerBean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    images.add(result.get(i).getImageUrl());
                }

                initViews();

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void initViews() {
        mPagerAdapter = new MyPagerAdapter(images, context);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true, new RotationPageTransformer());
        mViewPager.setOffscreenPageLimit(4);//设置预加载的数量，这里设置了4,会预加载中心item左边两个Item和右边两个Item
        mViewPager.setPageMargin(10);//设置两个Page之间的距离

    }


    private void doNextHttp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", 1 + "");
        map.put("count", 10 + "");
        new HttpHelper().get("movieApi/movie/v1/findComingSoonMovieList", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                NextMovieBean nextMovieBean = new Gson().fromJson(data, NextMovieBean.class);
                List<NextMovieBean.ResultBean> resultnext = nextMovieBean.getResult();
                nextMovieAdapter.setList(resultnext, context);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //正在上映
    private void doMovieingHttp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", 1 + "");
        map.put("count", 10 + "");
        new HttpHelper().get("movieApi/movie/v1/findReleaseMovieList", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                MovieingBean movieingBean = new Gson().fromJson(data, MovieingBean.class);
                List<MovieingBean.ResultBean> resulting = movieingBean.getResult();
                Log.i("TAG", "正在上映" + resulting.toString());
                movieingAdapter.setList(resulting, context);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //正在热映的请求
    private void doHothttp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", 1 + "");
        map.put("count", 10 + "");
        new HttpHelper().get("movieApi/movie/v1/findHotMovieList", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                HotMovieBean hotMovieBean = new Gson().fromJson(data, HotMovieBean.class);
                List<HotMovieBean.ResultBean> result = hotMovieBean.getResult();
                hotMovieAdapter.setList(result, context);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotMovie_image_moviefragment:
                context.startActivity(new Intent(context, MovieShowActivity.class));
                break;
            case R.id.Movieing_image_moviefragment:
                context.startActivity(new Intent(context, MovieShowActivity.class));
                break;
            case R.id.nextMovie_image_moviefragment:
                context.startActivity(new Intent(context, MovieShowActivity.class));
                break;
        }
    }
}
