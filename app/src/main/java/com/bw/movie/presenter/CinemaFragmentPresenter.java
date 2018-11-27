package com.bw.movie.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;

public class CinemaFragmentPresenter extends AppDelegate implements View.OnClickListener {

    private Button recommend;
    private Button nearby;

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
                break;
            case R.id.nearby:
                nearby.setBackgroundResource(R.drawable.edit_bg);
                recommend.setBackgroundResource(R.drawable.edit_bg1);
                break;
        }
    }
}
