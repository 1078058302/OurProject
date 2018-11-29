package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaDetailActivity;
import com.bw.movie.mvp.model.NearByBean;
import com.bw.movie.mvp.model.RecommendBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemaChildAdapter extends RecyclerView.Adapter<CinemaChildAdapter.ViewHolder> {
    private List<RecommendBean.ResultBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_show_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.image_cinema = view.findViewById(R.id.cinema_image);
        viewHolder.title = view.findViewById(R.id.title_cinema);
        viewHolder.desc = view.findViewById(R.id.desc_cinema);
        viewHolder.away = view.findViewById(R.id.away_cinema);
        viewHolder.collection = view.findViewById(R.id.collection);
        viewHolder.cinema_show = view.findViewById(R.id.cinema_show);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.image_cinema.setImageURI(list.get(i).getLogo());
        viewHolder.title.setText(list.get(i).getName());
        String address = list.get(i).getAddress();
        if (TextUtils.isEmpty(address)) {
            viewHolder.desc.setText("暂无详细地址");
        } else {
            viewHolder.desc.setText(address);
        }
        viewHolder.away.setText(list.get(i).getDistance() + "km");
        viewHolder.cinema_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CinemaDetailActivity.class);
                int id = list.get(i).getId();
                intent.putExtra("cinemaId", id);
                context.startActivity(intent);
            }
        });
        viewHolder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<RecommendBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        SimpleDraweeView image_cinema;
        TextView title;
        TextView desc;
        TextView away;
        RelativeLayout collection;
        LinearLayout cinema_show;
    }
}
