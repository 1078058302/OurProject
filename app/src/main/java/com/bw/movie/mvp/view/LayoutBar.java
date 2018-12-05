package com.bw.movie.mvp.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MapActivity;
import com.bw.movie.activity.ShowFindActivity;

public class LayoutBar extends RelativeLayout {

    private RelativeLayout find_item;
    private RelativeLayout find_tan;
    private ImageView find_image;
    private TextView find_sou;
    private EditText find_cinema;

    public LayoutBar(Context context) {
        super(context);
        init(context);
    }

    public LayoutBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LayoutBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(final Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.layout_bar, null);
        find_item = view.findViewById(R.id.find_item);
        find_tan = view.findViewById(R.id.find_tan);
        find_image = view.findViewById(R.id.find_image);
        find_sou = view.findViewById(R.id.find_sou);
        find_cinema = view.findViewById(R.id.find_cinema);
        find_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showShopCar();
            }
        });
        find_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hintShopCar();
            }
        });
        view.findViewById(R.id.localcation).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MapActivity.class));
            }
        });
        find_sou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String find = find_cinema.getText().toString().trim();
                Intent intent = new Intent(context, ShowFindActivity.class);
                intent.putExtra("find", find);
                context.startActivity(intent);
            }
        });
        addView(view);
    }

    private void showShopCar() {
        int width = this.getResources().getDisplayMetrics().widthPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(find_tan, "translationX", width, 0);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        find_tan.setVisibility(View.VISIBLE);
    }
    //关闭
    private void hintShopCar() {
        int width = this.getResources().getDisplayMetrics().widthPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(find_tan, "translationX", 0, width);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                find_tan.setVisibility(View.GONE);
            }
        }, 1000);

    }
}
