package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaDetailActivity;
import com.bw.movie.adapter.CinemaDetailsAdapter;
import com.bw.movie.adapter.PageMovieingAdapter;
import com.bw.movie.fragment.CommentFragment;
import com.bw.movie.fragment.DetailsShowFragment;
import com.bw.movie.mvp.model.CinemaDetailsBean;
import com.bw.movie.mvp.model.DetailsBean;
import com.bw.movie.mvp.model.MovieingBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
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
    private TabLayout tab_detail_progress;
    private FrameLayout show_details;
    private TextView details;
    private TextView comment;
    private View details_down;
    private View comment_down;
    private DetailsShowFragment detailsShowFragment;
    private int cinemaId;
    private List<MovieingBean.ResultBean> result = new ArrayList<>();
    private CinemaDetailsAdapter adapter1;
    private XRecyclerView xRecyclerView;
    private RelativeLayout relativeLayout;

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
        show_details = get(R.id.show_details);
        xRecyclerView = get(R.id.cinema_recycler);
        tab_detail_progress = (TabLayout) get(R.id.tab_detail_progress);
        down = get(R.id.down);
        details = get(R.id.details);
        comment = get(R.id.comment);
        relativeLayout = get(R.id.none);
        details_down = get(R.id.details_down);
        comment_down = get(R.id.comment_down);
        Intent intent = ((CinemaDetailActivity) context).getIntent();
        cinemaId = intent.getIntExtra("cinemaId", 0);
        SharedPreferencesUtils.putInt(context, "cinemaId", cinemaId);
        adapter = new PageMovieingAdapter();
        details_cover.setAdapter(adapter);
        doHttp(cinemaId);
        doDetailsHttp(cinemaId);
        detailsShowFragment = new DetailsShowFragment();
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
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details_down.setVisibility(View.VISIBLE);
                comment_down.setVisibility(View.GONE);
                SharedPreferencesUtils.putInt(context, "cinemaId", cinemaId);
                showFragment(detailsShowFragment);

            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_down.setVisibility(View.VISIBLE);
                details_down.setVisibility(View.GONE);
                SharedPreferencesUtils.putInt(context, "cinemaId", cinemaId);
                CommentFragment commentFragment = new CommentFragment();
                showFragment(commentFragment);
            }
        });


        showFragment(detailsShowFragment);
        tab_detail_progress.setTabMode(TabLayout.MODE_FIXED);
        tab_detail_progress.setSelectedTabIndicatorHeight(10);

        tab_detail_progress.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                details_cover.scrollToPosition(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//http://172.17.8.100/movieApi/movie/v1/findMovieScheduleList?cinemasId=2&movieId=3

        details_cover.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                tab_detail_progress.getTabAt(position).select();
                final int id = result.get(position).getId();
//                Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
                final int cinemaId = SharedPreferencesUtils.getInt(context, "cinemaId");
                adapter1 = new CinemaDetailsAdapter();
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                xRecyclerView.setLayoutManager(manager);
                xRecyclerView.setAdapter(adapter1);
                doDetail(id, cinemaId);
                xRecyclerView.setPullRefreshEnabled(true);
                xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        doDetail(id, cinemaId);
                    }

                    @Override
                    public void onLoadMore() {
                        xRecyclerView.loadMoreComplete();
                    }
                });
            }
        });


    }

    private void doDetail(int id, int cinemaId) {
        //?cinemasId=2&movieId=3
        Map map = new HashMap();
        map.put("cinemasId", cinemaId);
        map.put("movieId", id);
        new HttpHelper().get("/movieApi/movie/v1/findMovieScheduleList", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                CinemaDetailsBean cinemaDetailsBean = new Gson().fromJson(data, CinemaDetailsBean.class);
                List<CinemaDetailsBean.ResultBean> result = cinemaDetailsBean.getResult();
                adapter1.setList(result);
                adapter1.setContext(context);
                xRecyclerView.refreshComplete();

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void showFragment(Fragment fragment) {
        FragmentManager sfm = ((CinemaDetailActivity) context).getSupportFragmentManager();
        sfm.beginTransaction().replace(R.id.show_details, fragment).commit();
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

    private void doHttp(int cinemaId) {
        ///movieApi/movie/v1/findReleaseMovieList?page=1&count=100
        //http://172.17.8.100/movieApi/movie/v1/findMovieListByCinemaId?cinemaId=3
        Map map = new HashMap<>();
        map.put("cinemaId", cinemaId);
        new HttpHelper().get("/movieApi/movie/v1/findMovieListByCinemaId", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                MovieingBean movieingBean = new Gson().fromJson(data, MovieingBean.class);
                result = movieingBean.getResult();
                int size = result.size();
                for (int i = 0; i < size; i++) {
                    tab_detail_progress.addTab(tab_detail_progress.newTab().setText(null));
                }
                adapter.setContext(context);
                adapter.setList(result);
                details_cover.scrollToPosition(result.size() / 2);
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
        details_show.setVisibility(View.GONE);
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
