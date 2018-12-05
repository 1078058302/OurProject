package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.BuyTicketActivity;
import com.bw.movie.activity.SuccessShowActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.utils.UltimateBar;

public class SuccessShowActivityPresenter extends AppDelegate {
    @Override
    public int getLayoutId() {
        return R.layout.activity_success_show;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((SuccessShowActivity) context).apply();
        Intent intent = ((SuccessShowActivity) context).getIntent();
        int price = intent.getIntExtra("price", 0);
        TextView price_one = get(R.id.price_one);
        price_one.setText(price + ".00");
        get(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SuccessShowActivity) context).finish();
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
