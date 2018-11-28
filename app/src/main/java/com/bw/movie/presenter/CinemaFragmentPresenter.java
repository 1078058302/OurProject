package com.bw.movie.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.fragment.CinemaChild1Fragment;
import com.bw.movie.fragment.CinemaChildFragment;
import com.bw.movie.mvp.view.AppDelegate;

public class CinemaFragmentPresenter extends AppDelegate implements View.OnClickListener {

    private Button recommend;
    private Button nearby;
    private CinemaChildFragment childFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cinema;
    }

    @Override
    public void initData() {
        super.initData();
        recommend = get(R.id.recommend);
        nearby = get(R.id.nearby);
        setClick(this, R.id.recommend, R.id.nearby);
        childFragment = new CinemaChildFragment();
        showFragment(childFragment);
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommend:
                recommend.setBackgroundResource(R.drawable.edit_bg);
                nearby.setBackgroundResource(R.drawable.edit_bg1);
                showFragment(childFragment);
                break;
            case R.id.nearby:
                nearby.setBackgroundResource(R.drawable.edit_bg);
                recommend.setBackgroundResource(R.drawable.edit_bg1);
                CinemaChild1Fragment child1Fragment = new CinemaChild1Fragment();
                showFragment(child1Fragment);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager sfm = ((MainActivity) context).getSupportFragmentManager();
        sfm.beginTransaction().replace(R.id.show_frag, fragment).commit();
    }
}
