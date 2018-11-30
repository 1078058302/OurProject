package com.bw.movie.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.adapter.ViewPagerAdapter;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.MineFragment;
import com.bw.movie.fragment.MovieShowFragment;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends AppDelegate implements View.OnClickListener {

    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager main_vp;
    private ImageView movie_main;
    private ImageView cinema_main;
    private ImageView mine_main;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((MainActivity) context).apply();
        main_vp = get(R.id.main_vp);
        //加入三个fragment
        fragments.add(new MovieShowFragment());
        fragments.add(new CinemaFragment());
        fragments.add(new MineFragment());
        ViewPagerAdapter adapter = new ViewPagerAdapter(((MainActivity) context).getSupportFragmentManager());
        adapter.setList(fragments);
        main_vp.setAdapter(adapter);
        movie_main = get(R.id.movie_main);
        cinema_main = get(R.id.cinema_main);
        mine_main = get(R.id.mine_main);
        setClick(this, R.id.movie_main, R.id.cinema_main, R.id.mine_main);
        main_vp.setOffscreenPageLimit(3);

        main_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        movie_main.setImageResource(R.drawable.icon_film_selected_xhdpi);
                        cinema_main.setImageResource(R.drawable.icon_cinema_default_hdpi);
                        mine_main.setImageResource(R.drawable.icon_my_default_hdip);
                        break;
                    case 1:
                        cinema_main.setImageResource(R.drawable.icon_cinema_selected_hdpi);
                        movie_main.setImageResource(R.drawable.icon_film_fault_hdpi);
                        mine_main.setImageResource(R.drawable.icon_my_default_hdip);
                        break;
                    case 2:
                        mine_main.setImageResource(R.drawable.icon_my_selected_xhdpi);
                        movie_main.setImageResource(R.drawable.icon_film_fault_hdpi);
                        cinema_main.setImageResource(R.drawable.icon_cinema_default_hdpi);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_main:
                main_vp.setCurrentItem(0);
                movie_main.setImageResource(R.drawable.icon_film_selected_xhdpi);
                cinema_main.setImageResource(R.drawable.icon_cinema_default_hdpi);
                mine_main.setImageResource(R.drawable.icon_my_default_hdip);
                break;
            case R.id.cinema_main:
                main_vp.setCurrentItem(1);
                cinema_main.setImageResource(R.drawable.icon_cinema_selected_hdpi);
                movie_main.setImageResource(R.drawable.icon_film_fault_hdpi);
                mine_main.setImageResource(R.drawable.icon_my_default_hdip);
                break;
            case R.id.mine_main:
                main_vp.setCurrentItem(2);
                mine_main.setImageResource(R.drawable.icon_my_selected_xhdpi);
                movie_main.setImageResource(R.drawable.icon_film_fault_hdpi);
                cinema_main.setImageResource(R.drawable.icon_cinema_default_hdpi);
                break;
        }
    }
}
