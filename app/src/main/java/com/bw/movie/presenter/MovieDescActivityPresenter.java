package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class MovieDescActivityPresenter extends AppDelegate implements View.OnClickListener {

    private int movie_id;
    private TextView moviename;
    private ImageView movienameimage;
    private RelativeLayout movie_desc_relative_details;
    private RelativeLayout relative;
    private RelativeLayout movie_desc_relative_notice;
    private RelativeLayout movie_desc_relative_stills;
    private RelativeLayout movie_desc_relative_evaluate;
    private TextView details_type;
    private TextView details_director;
    private TextView details_time;
    private TextView details_address;
    private ImageView details_image_desc;
    private ImageView details_down_image;
    private TextView details_introduce;

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_desc;
    }

    @Override
    public void initData() {
        super.initData();
        moviename = get(R.id.movie_desc_name);
        movienameimage = get(R.id.movie_image_name);
        //详情
        Button details = get(R.id.movie_desc_bt_details);
        //预告
        Button Notice = get(R.id.movie_desc_bt_Notice);
        //剧照
        Button Stills = get(R.id.movie_desc_bt_Stills);
        //影评
        Button evaluate = get(R.id.movie_desc_bt_evaluate);


        //详情 id
        details_type = get(R.id.details_type);
        details_director = get(R.id.details_director);
        details_time = get(R.id.details_time);
        details_address = get(R.id.details_address);
        details_image_desc = get(R.id.details_image_desc);
        details_down_image = get(R.id.details_down_image);
        details_introduce = get(R.id.details_introduce);


        //详情
        movie_desc_relative_details = get(R.id.movie_desc_relative_details);
        //预告
        movie_desc_relative_notice = get(R.id.movie_desc_relative_Notice);
        //剧照
        movie_desc_relative_stills = get(R.id.movie_desc_relative_Stills);
        //影评
        movie_desc_relative_evaluate = get(R.id.movie_desc_relative_evaluate);
        //点击事件
        setClick(this, R.id.movie_desc_bt_details, R.id.movie_desc_bt_Notice, R.id.movie_desc_bt_Stills, R.id.movie_desc_bt_evaluate,R.id.details_down_image);
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
                //详情
                dodetails(result);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //详情layout赋值
    private void dodetails(MovieDescBean.ResultBean result) {
        Glide.with(context).load(result.getImageUrl()).into(details_image_desc);
        details_type.setText("类型: "+result.getMovieTypes());
        details_director.setText("导演: "+result.getDirector());
        details_time.setText("时长: "+result.getDuration());
        details_address.setText("产地: "+result.getPlaceOrigin());
        details_introduce.setText(result.getSummary());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //详情
            case R.id.movie_desc_bt_details:
                relative = movie_desc_relative_details;
                showShopCar(relative);
                break;
            //预告
            case R.id.movie_desc_bt_Notice:
                relative = movie_desc_relative_notice;
                showShopCar(relative);
                break;
            //剧照
            case R.id.movie_desc_bt_Stills:
                relative = movie_desc_relative_stills;
                showShopCar(relative);
                break;
            //影评
            case R.id.movie_desc_bt_evaluate:
                relative = movie_desc_relative_evaluate;
                showShopCar(relative);
                break;
            case R.id.details_down_image:
                relative = movie_desc_relative_details;
                hintShopCar(relative);
                break;
        }
    }

    private void showShopCar(RelativeLayout relative) {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relative, "translationY", heightPixels, 0);
        objectAnimator.setDuration(400);
        objectAnimator.start();
        relative.setVisibility(View.VISIBLE);
    }

    //关闭
    private void hintShopCar(final RelativeLayout relative) {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relative, "translationY", 0, heightPixels);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relative.setVisibility(View.GONE);
            }
        }, 400);

    }
}
