package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import com.bw.movie.adapter.NextMovieAdapter;
import com.bw.movie.adapter.PageShowAdapter;
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

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class MovieShowFragmentPresenter extends AppDelegate implements View.OnClickListener {

    private RecyclerView hotRecycle;
    HotMovieAdapter hotMovieAdapter = new HotMovieAdapter();
    MovieingAdapter movieingAdapter = new MovieingAdapter();
    NextMovieAdapter nextMovieAdapter = new NextMovieAdapter();
    private RecyclerView nextRecycle;
    private RecyclerView movieingRecycle;
    private List<String> images = new ArrayList<>();

    private ProgressBar progress_page;
    private RecyclerCoverFlow mList;
    private TabLayout tab_main;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initData() {
        super.initData();
        /*   progress_page = (ProgressBar) get(R.id.progress_page);*/
        ImageView nextMovie = get(R.id.nextMovie_image_moviefragment);
        ImageView hotMovie = get(R.id.hotMovie_image_moviefragment);
        ImageView Movieing = get(R.id.Movieing_image_moviefragment);
        mList = get(R.id.list);
        tab_main = (TabLayout) get(R.id.tab_main);
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
        tab_main.setTabMode(TabLayout.MODE_FIXED);
        tab_main.setSelectedTabIndicatorHeight(10);

        tab_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mList.scrollToPosition(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                tab_main.getTabAt(position).select();
            }
        });
    }



    //顶部图片轮播
    private void doHttp() {
        new HttpHelper().get("/movieApi/movie/v1/findHotMovieList?page=1&count=6", null).result(new HttpListener() {
            @Override
            public void success(String data) {
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                List<BannerBean.ResultBean> result = bannerBean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    tab_main.addTab(tab_main.newTab().setText(null));
                }
                initViews(result);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void initViews(List<BannerBean.ResultBean> result) {
//        mList.setFlatFlow(true); //平面滚动
        PageShowAdapter myPagerAdapter = new PageShowAdapter();
        myPagerAdapter.setList(result);
        myPagerAdapter.setContext(context);
        mList.setAdapter(myPagerAdapter);
        mList.scrollToPosition(3);

        myPagerAdapter.result(new PageShowAdapter.PageListener() {
            @Override
            public void backId(int i) {
                mList.scrollToPosition(i);
            }
        });
    }

    //即将上映
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
        String movie = "movie";
        Intent intent = new Intent(context, MovieShowActivity.class);
        switch (view.getId()) {
            case R.id.hotMovie_image_moviefragment://热门电影
                intent.putExtra(movie, "1");
                context.startActivity(intent);
                break;
            case R.id.Movieing_image_moviefragment://正在上映
                intent.putExtra(movie, "2");
                context.startActivity(intent);
                break;
            case R.id.nextMovie_image_moviefragment://即将上映

                intent.putExtra(movie, "3");
                context.startActivity(intent);
                break;
        }
    }
}
