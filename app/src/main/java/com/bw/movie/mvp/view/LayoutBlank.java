package com.bw.movie.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bw.movie.R;

public class LayoutBlank extends RelativeLayout {
    public LayoutBlank(Context context) {
        super(context);
        initView(context);
    }

    public LayoutBlank(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LayoutBlank(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        RelativeLayout view = (RelativeLayout) View.inflate(context, R.layout.layout_blank, null);
        ImageView blank = view.findViewById(R.id.movieshow_image_retreat);
        blank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });
        addView(view);
    }
}
