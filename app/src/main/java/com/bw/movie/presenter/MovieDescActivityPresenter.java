package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.mvp.model.MovieDescBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MovieDescActivityPresenter extends AppDelegate {

    private int movie_id;
    private TextView moviename;
    private ImageView movienameimage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_desc;
    }

    @Override
    public void initData() {
        super.initData();
        moviename = get(R.id.movie_desc_name);
        movienameimage = get(R.id.movie_image_name);
        ChenJinShi();
        Intent intent = ((MovieDescActivity) context).getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        doMovieDescHttp();
    }

    private void ChenJinShi() {
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((MovieDescActivity) context)
                .apply();
    }

    private void doMovieDescHttp() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movie_id + "");
        new HttpHelper().get("movieApi/movie/v1/findMoviesDetail", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                MovieDescBean movieDescBean = new Gson().fromJson(data, MovieDescBean.class);
                MovieDescBean.ResultBean result = movieDescBean.getResult();
                moviename.setText(result.getName());
                Glide.with(context).load(result.getImageUrl()).into(movienameimage);
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
