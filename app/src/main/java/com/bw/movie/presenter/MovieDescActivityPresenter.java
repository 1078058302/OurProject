package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.FilmCinemaActivity;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.adapter.MovieDetailsAdapter;
import com.bw.movie.adapter.MovieDonoticeAdapter;
import com.bw.movie.adapter.MovieEvaluateAdapter;
import com.bw.movie.adapter.MovieStillsAdapter;
import com.bw.movie.mvp.model.EvaluateBean;
import com.bw.movie.mvp.model.MovieDescBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private XRecyclerView details_xrecycle;
    private RecyclerView stills_recycle;
    private ImageView stills_down_image;
    private MovieStillsAdapter movieStillsAdapter;
    private RecyclerView evaluate_recycle;
    private ImageView evaluate_down_image;
    private ImageView notice_down_image;
    private MovieEvaluateAdapter movieEvaluateAdapter;
    private TextView buy_film;
    private MovieDescBean.ResultBean result = null;
    private RecyclerView notice_recycle;


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
        details_xrecycle = get(R.id.details_xrecycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        details_xrecycle.setLayoutManager(linearLayoutManager);

        //剧照id
        stills_recycle = get(R.id.stills_recycle);
        stills_down_image = get(R.id.stills_down_image);

        //预告id
        notice_down_image = get(R.id.notice_down_image);
        notice_recycle = get(R.id.Notice_recycle);

        //影评id
        evaluate_recycle = get(R.id.evaluate_recycle);
        evaluate_down_image = get(R.id.evaluate_down_image);


        //详情
        movie_desc_relative_details = get(R.id.movie_desc_relative_details);
        //预告
        movie_desc_relative_notice = get(R.id.movie_desc_relative_Notice);
        //剧照
        movie_desc_relative_stills = get(R.id.movie_desc_relative_Stills);
        //影评
        movie_desc_relative_evaluate = get(R.id.movie_desc_relative_evaluate);
        //点击事件
        setClick(this, R.id.movie_desc_bt_details, R.id.movie_desc_bt_Notice, R.id.movie_desc_bt_Stills, R.id.movie_desc_bt_evaluate, R.id.details_down_image, R.id.notice_down_image, R.id.stills_down_image, R.id.evaluate_down_image);
        ChenJinShi();
        Intent intent = ((MovieDescActivity) context).getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        //展示电影详情
        if (!TextUtils.isEmpty(movie_id + "")) {
            SharedPreferencesUtils.putInt(context, "movice_ids", movie_id);
        }
        doMovieDescHttp();
        //展示影评
        doevaluate();

        movieEvaluateAdapter = new MovieEvaluateAdapter(context);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        evaluate_recycle.setLayoutManager(linearLayoutManager1);
        evaluate_recycle.setAdapter(movieEvaluateAdapter);
        buy_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmCinemaActivity.class);
                if (result != null) {
                    if (result.getName() != null) {
                        intent.putExtra("name", result.getName());
                    } else {
                        intent.putExtra("name", "请购买");

                    }
                    ((MovieDescActivity) context).startActivity(intent);

                }

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
                //详情
                dodetails(result);
                //剧照
                dostills(result);
                //预告
                donotice(result);
            }

            @Override
            public void fail(String error) {

            }
        });
    }
    //预告片
    private void donotice(MovieDescBean.ResultBean result) {
        List<MovieDescBean.ResultBean.ShortFilmListBean> shortFilmList = result.getShortFilmList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notice_recycle.setLayoutManager(linearLayoutManager);
        MovieDonoticeAdapter movieDonoticeAdapter = new MovieDonoticeAdapter(context);
        movieDonoticeAdapter.setList(shortFilmList);
        notice_recycle.setAdapter(movieDonoticeAdapter);
    }

    //影评layout赋值
    private void doevaluate() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movie_id + "");
        map.put("page", 1 + "");
        map.put("count", 10 + "");
        new HttpHelper().get("movieApi/movie/v1/findAllMovieComment", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Log.i("TAG", "data................." + data.toString());
                EvaluateBean evaluateBean = new Gson().fromJson(data, EvaluateBean.class);
                List<EvaluateBean.ResultBean> result = evaluateBean.getResult();
                movieEvaluateAdapter.setList(result);
            }

            @Override
            public void fail(String error) {

            }
        });


    }

    //剧照layout赋值
    private void dostills(MovieDescBean.ResultBean result) {
        List<String> posterList = result.getPosterList();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager( context, 2 );
        stills_recycle.setLayoutManager(staggeredGridLayoutManager);
        movieStillsAdapter = new MovieStillsAdapter(context);
        stills_recycle.setAdapter(movieStillsAdapter);
        movieStillsAdapter.setList(posterList);
    }

    //详情layout赋值
    private void dodetails(MovieDescBean.ResultBean result) {
        Glide.with(context).load(result.getImageUrl()).into(details_image_desc);
        details_type.setText("类型: " + result.getMovieTypes());
        details_director.setText("导演: " + result.getDirector());
        details_time.setText("时长: " + result.getDuration());
        details_address.setText("产地: " + result.getPlaceOrigin());
        details_introduce.setText(result.getSummary());
        String starring = result.getStarring();
        String[] split = starring.split(",");
        ArrayList<String> nameList = new ArrayList<String>();
        for (int i = 0; i < split.length; i++) {
            nameList.add(split[i]);
        }
        MovieDetailsAdapter movieDetailsAdapter = new MovieDetailsAdapter(context);
        movieDetailsAdapter.setList(nameList);
        details_xrecycle.setAdapter(movieDetailsAdapter);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //+++++++++++++++++++++++++++++++++++++++++++++++++显示++++++++++++++++++++++++++++++++++++++++++++++++++++
            //详情showLayout
            case R.id.movie_desc_bt_details:
                relative = movie_desc_relative_details;
                showShopCar(relative);
                break;
            //预告showLayout
            case R.id.movie_desc_bt_Notice:
                relative = movie_desc_relative_notice;
                showShopCar(relative);
                break;
            //剧照showLayout
            case R.id.movie_desc_bt_Stills:
                relative = movie_desc_relative_stills;
                showShopCar(relative);
                break;
            //影评showLayout
            case R.id.movie_desc_bt_evaluate:
                relative = movie_desc_relative_evaluate;
                showShopCar(relative);

                break;
            //+++++++++++++++++++++++++++++++++++++++++++++++++隐藏++++++++++++++++++++++++++++++++++++++++++++++++++++
            //详情hintlayout
            case R.id.details_down_image:
                relative = movie_desc_relative_details;
                hintShopCar(relative);
            case R.id.notice_down_image:
                relative = movie_desc_relative_notice;
                hintShopCar(relative);
                break;
            //剧照hintlayout
            case R.id.stills_down_image:
                relative = movie_desc_relative_stills;
                hintShopCar(relative);
                break;
            //影评hintlayout
            case R.id.evaluate_down_image:
                relative = movie_desc_relative_evaluate;
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
