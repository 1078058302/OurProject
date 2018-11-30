package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.FilmCinemaActivity;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.mvp.model.MovieDescBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MovieDescActivityPresenter extends AppDelegate {

    private int movie_id;
    private TextView moviename;
    private ImageView movienameimage;
    private TextView buy_film;
    private MovieDescBean.ResultBean result = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_desc;
    }

    @Override
    public void initData() {
        super.initData();
        buy_film = (TextView) get(R.id.buy_film);
        moviename = get(R.id.movie_desc_name);
        movienameimage = get(R.id.movie_image_name);
        ChenJinShi();
        Intent intent = ((MovieDescActivity) context).getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        if (!TextUtils.isEmpty(movie_id +"")) {
            SharedPreferencesUtils.putInt(context,"movice_ids",movie_id);
        }
        doMovieDescHttp();
        buy_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmCinemaActivity.class);
                if (result.getName() != null) {
                    intent.putExtra("name", result.getName());
                } else {
                    intent.putExtra("name", "请购买");

                }
                ((MovieDescActivity) context).startActivity(intent);

            }

        });


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
                result = movieDescBean.getResult();
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
