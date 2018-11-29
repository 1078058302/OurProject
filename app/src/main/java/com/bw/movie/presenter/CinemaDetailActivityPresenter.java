package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaDetailActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.adapter.PageMovieingAdapter;
import com.bw.movie.mvp.model.DetailsBean;
import com.bw.movie.mvp.model.MovieingBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.UltimateBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDetailActivityPresenter extends AppDelegate {

    private SimpleDraweeView details_image;
    private TextView details_name;
    private TextView details_desc;
    private PageMovieingAdapter adapter;
    private RecyclerCoverFlow details_cover;
    private String name;
    private String address;
    private String logo;
    private LinearLayout details_show;
    private ImageView down;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cinema_detail;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((CinemaDetailActivity) context).apply();
        details_image = get(R.id.cinema_details_image);
        details_name = get(R.id.cinema_details_name);
        details_desc = get(R.id.cinema_details_desc);
        details_cover = get(R.id.details_cover);
        details_show = get(R.id.details_show);
        down = get(R.id.down);
        Intent intent = ((CinemaDetailActivity) context).getIntent();
        int cinemaId = intent.getIntExtra("cinemaId", 0);
        Toast.makeText(context, cinemaId + "", Toast.LENGTH_SHORT).show();
        adapter = new PageMovieingAdapter();
        details_cover.setAdapter(adapter);
        doHttp();
        doDetailsHttp(cinemaId);
        adapter.result(new PageMovieingAdapter.PageListener() {
            @Override
            public void backId(int i) {
                details_cover.scrollToPosition(i);
            }
        });
        details_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShopCar();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintShopCar();
            }
        });
    }

    private void doDetailsHttp(int cinemaId) {
        ///movieApi/cinema/v1/findCinemaInfo?cinemaId=1
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId + "");
        new HttpHelper().get("/movieApi/cinema/v1/findCinemaInfo", map).result(new HttpListener() {
            @Override
            public void success(String data) {
//                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                DetailsBean detailsBean = new Gson().fromJson(data, DetailsBean.class);
                DetailsBean.ResultBean result = detailsBean.getResult();
                name = result.getName();
                address = result.getAddress();
                logo = result.getLogo();
                details_name.setText(name);
                details_desc.setText(address);
                details_image.setImageURI(logo);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doHttp() {
        ///movieApi/movie/v1/findReleaseMovieList?page=1&count=100
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "60");
        new HttpHelper().get("/movieApi/movie/v1/findReleaseMovieList", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                MovieingBean movieingBean = new Gson().fromJson(data, MovieingBean.class);
                List<MovieingBean.ResultBean> result = movieingBean.getResult();
                adapter.setContext(context);
                adapter.setList(result);
                details_cover.scrollToPosition(7);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void showShopCar() {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(details_show, "translationY", heightPixels, 0);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        details_show.setVisibility(View.VISIBLE);
    }

    //关闭
    private void hintShopCar() {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(details_show, "translationY", 0, heightPixels);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                details_show.setVisibility(View.GONE);
            }
        }, 1000);

    }

    public void setContext(Context context) {
        this.context = context;
    }
}
