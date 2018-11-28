package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.MovieShowActivity;
import com.bw.movie.adapter.HotMovieShowAdapter;
import com.bw.movie.mvp.model.HotMovieBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

/*
 * insert zhang
 * 电影详情
 */
public class MovieShowActivityPresenter extends AppDelegate implements View.OnClickListener {
    private ImageView retreat;
    private RecyclerView recycle_movieshow;
    HotMovieShowAdapter hotMovieShowAdapter = new HotMovieShowAdapter();

    @Override
    public int getLayoutId() {

        return R.layout.activity_movie_show;
    }

    @Override
    public void initData() {
        super.initData();
        //点击图片返回
        ImageView retreat = get(R.id.movieshow_image_retreat);
        Button hot_bt = get(R.id.hot_bt_movieshow);
        Button ing_bt = get(R.id.moviing_bt_movieshow);
        Button next_bt = get(R.id.next_bt_movieshow);
        recycle_movieshow = get(R.id.recycle_movieshow);
        setClick(this, R.id.movieshow_image_retreat, R.id.movieshow_image_retreat, R.id.hot_bt_movieshow, R.id.moviing_bt_movieshow, R.id.next_bt_movieshow);
        ChenJinShi();
    }

    private void ChenJinShi() {
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((MovieShowActivity) context)
                .apply();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.movieshow_image_retreat:
                ((MovieShowActivity) context).finish();
                break;
            //热门播放
            case R.id.hot_bt_movieshow:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycle_movieshow.setLayoutManager(linearLayoutManager);
                recycle_movieshow.setAdapter(hotMovieShowAdapter);
                doHotHttp();
                break;
            //正在播放
            case R.id.moviing_bt_movieshow:
                doMovieingHttp();
                break;
            //即将播放
            case R.id.next_bt_movieshow:
                doNextHttp();
                break;
        }
    }

    //即将播放
    private void doNextHttp() {

    }

    //正在播放
    private void doMovieingHttp() {

    }

    //热门播放
    private void doHotHttp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", 1 + "");
        map.put("count", 10 + "");
        new HttpHelper().get("movieApi/movie/v1/findHotMovieList", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                HotMovieBean hotMovieBean = new Gson().fromJson(data, HotMovieBean.class);
                List<HotMovieBean.ResultBean> result = hotMovieBean.getResult();
                hotMovieShowAdapter.setlist(context, result);
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
