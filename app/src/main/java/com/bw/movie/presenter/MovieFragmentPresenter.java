package com.bw.movie.presenter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.MyPagerAdapter;
import com.bw.movie.adapter.RotationPageTransformer;
import com.bw.movie.model.BannerBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MovieFragmentPresenter extends AppDelegate {
    private List<String> images=new ArrayList<>();
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
        progress_page = (ProgressBar) get(R.id.progress_page);
        //滑动监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        doHttp();





    }

    private void doHttp() {
        new HttpHelper().get("/movieApi/movie/v1/findHotMovieList?page=1&count=6",null).result(new HttpListener() {
            @Override
            public void success(String data) {
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                List<BannerBean.ResultBean> result = bannerBean.getResult();
                for(int i=0;i<result.size();i++){
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


    public void setContext(Context context) {
        this.context = context;
    }
}
