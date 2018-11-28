package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.NearByBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemaChild1Adapter extends RecyclerView.Adapter<CinemaChild1Adapter.ViewHolder> {
    private List<NearByBean.ResultBean.NearbyCinemaListBean> list = new ArrayList<>();
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.image_cinema.setImageURI(list.get(i).getLogo());
        viewHolder.title.setText(list.get(i).getName());
        String address = list.get(i).getAddress();
        if (TextUtils.isEmpty(address)) {
            viewHolder.desc.setText("暂无详细地址");
        } else {
            viewHolder.desc.setText(address);
        }

        viewHolder.away.setText(list.get(i).getDistance() + "km");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<NearByBean.ResultBean.NearbyCinemaListBean> list) {
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
    }
}
