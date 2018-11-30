package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.CinemaCityActivity;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.mvp.model.MovieDescBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
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


        Intent intent = ((CinemaCityActivity) context).getIntent();
        address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");
        String logo = intent.getStringExtra("logo");
        filmname = SharedPreferencesUtils.getString(context, "filmname");
        movice_ids = SharedPreferencesUtils.getInt(context, "movice_ids");

        doMovieDescHttp();

    }


    private void doMovieDescHttp() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movice_ids + "");
        new HttpHelper().get("movieApi/movie/v1/findMoviesDetail", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                toast(data);
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
