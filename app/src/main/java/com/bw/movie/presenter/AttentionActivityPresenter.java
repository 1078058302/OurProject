package com.bw.movie.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.activity.AttentionActivity;
import com.bw.movie.fragment.CinemasFragment;
import com.bw.movie.fragment.FilmFragment;
import com.bw.movie.mvp.view.AppDelegate;

import java.util.ArrayList;
import java.util.List;

public class AttentionActivityPresenter extends AppDelegate implements View.OnClickListener {
    private Button bt_film;
    private Button bt_cinema;
    private ViewPager view_vp;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    public void initData() {
        super.initData();
        bt_film = (Button) get(R.id.bt_film);
        bt_cinema = (Button) get(R.id.bt_cinema);
        view_vp = (ViewPager) get(R.id.view_vp);
        setClick(this, R.id.bt_film, R.id.bt_cinema);
        fragmentList.add(new FilmFragment());
        fragmentList.add(new CinemasFragment());
        view_vp.setAdapter(new FragmentPagerAdapter(((AttentionActivity) context).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        view_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        bt_film.setBackgroundResource(R.drawable.edit_bg);
                        bt_cinema.setBackgroundResource(R.drawable.edit_bg1);
                        break;
                    case 1:
                        bt_cinema.setBackgroundResource(R.drawable.edit_bg);
                        bt_film.setBackgroundResource(R.drawable.edit_bg1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_film:
                //电影
                view_vp.setCurrentItem(0);
                bt_film.setBackgroundResource(R.drawable.edit_bg);
                bt_cinema.setBackgroundResource(R.drawable.edit_bg1);
                break;
            case R.id.bt_cinema:
                //影院
                view_vp.setCurrentItem(1);
                bt_cinema.setBackgroundResource(R.drawable.edit_bg);
                bt_film.setBackgroundResource(R.drawable.edit_bg1);
                break;
        }
    }
}
