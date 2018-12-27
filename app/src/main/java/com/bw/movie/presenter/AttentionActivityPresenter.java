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
import com.bw.movie.utils.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class AttentionActivityPresenter extends AppDelegate implements View.OnClickListener {
    private Button mBt_film,mBt_cinema;
    private ViewPager mView_vp;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((AttentionActivity) context).apply();
        mBt_film = (Button) get(R.id.bt_film);
        mBt_cinema = (Button) get(R.id.bt_cinema);
        mView_vp = (ViewPager) get(R.id.view_vp);
        setClick(this, R.id.bt_film, R.id.bt_cinema);
        mFragmentList.add(new FilmFragment());
        mFragmentList.add(new CinemasFragment());
        mView_vp.setAdapter(new FragmentPagerAdapter(((AttentionActivity) context).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentList.get(i);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        mView_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mBt_film.setBackgroundResource(R.drawable.edit_bg);
                        mBt_cinema.setBackgroundResource(R.drawable.edit_bg1);
                        break;
                    case 1:
                        mBt_cinema.setBackgroundResource(R.drawable.edit_bg);
                        mBt_film.setBackgroundResource(R.drawable.edit_bg1);
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
                mView_vp.setCurrentItem(0);
                mBt_film.setBackgroundResource(R.drawable.edit_bg);
                mBt_cinema.setBackgroundResource(R.drawable.edit_bg1);
                break;
            case R.id.bt_cinema:
                //影院
                mView_vp.setCurrentItem(1);
                mBt_cinema.setBackgroundResource(R.drawable.edit_bg);
                mBt_film.setBackgroundResource(R.drawable.edit_bg1);
                break;
        }
    }
}
