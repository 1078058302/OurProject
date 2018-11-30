package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.activity.StartActivity;
import com.bw.movie.activity.WelcomeActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class WelcomeActivityPresenter extends AppDelegate {

    private BGABanner banner_welcome;
    private List<Integer> mPics=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false)
                .build((WelcomeActivity) context).apply();
        mPics.add(R.mipmap.introducer1);
        mPics.add(R.mipmap.introducer2);
        mPics.add(R.mipmap.introducer3);
        mPics.add(R.mipmap.introducer4);
        SharedPreferencesUtils.putBoolean(context, "isFirst", true);
        banner_welcome = (BGABanner) get(R.id.banner_welcome);
        banner_welcome.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                Glide.with(context).load(mPics.get(position)).into((ImageView) itemView);
            }
        });
        banner_welcome.setData(mPics,null);
        //滑动监听
        banner_welcome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i==3) {
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((WelcomeActivity) context).finish();
                }
            }


            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });







    }



    public void getContext(Context context) {
        this.context = context;
    }

}
