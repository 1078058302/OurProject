package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaDetailActivity;
import com.bw.movie.mvp.model.ShowBean;
import com.bw.movie.mvp.model.TrueRecommendBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrueReconedAdapter extends RecyclerView.Adapter<TrueReconedAdapter.ViewHolder> {
    private List<ShowBean> list = new ArrayList<>();
    private Context context;
    private String sessionId;
    private int userId;
    private boolean followCinema;
    private int id;
    public boolean b = true;

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
        viewHolder.collection_image = view.findViewById(R.id.collection_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (list.isEmpty()){
            return;
        }
        viewHolder.image_cinema.setImageURI(list.get(i).getLogo());
        viewHolder.title.setText(list.get(i).getName());
        String address = list.get(i).getAddress();
        if (TextUtils.isEmpty(address)) {
            viewHolder.desc.setText("暂无详细地址");
        } else {
            viewHolder.desc.setText(address);
        }
        double distance = list.get(i).getDistance();
        double v = distance / 1000;
        viewHolder.away.setText(v + "km");
        id = list.get(i).getId();
        sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        userId = SharedPreferencesUtils.getInt(context, "userId");
        final int id = list.get(i).getId();
        viewHolder.cinema_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CinemaDetailActivity.class);

                intent.putExtra("cinemaId", id);
                context.startActivity(intent);
            }
        });
        followCinema = list.get(i).isFollowCinema();
        if (followCinema) {
            viewHolder.collection_image.setImageResource(R.mipmap.collection_selected);
        } else {
            viewHolder.collection_image.setImageResource(R.mipmap.collection_default);
        }
        Log.i("TrueReconedAdapter", followCinema + "");

        viewHolder.collection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sessionId)) {
                    if (followCinema) {
                        list.get(i).setFollowCinema(false);
                        doHttpNo(id, i, viewHolder);
                        viewHolder.collection_image.setImageResource(R.mipmap.collection_default);
                    } else {
                        list.get(i).setFollowCinema(true);
                        doHttpYes(id, i, viewHolder);
                        viewHolder.collection_image.setImageResource(R.mipmap.collection_selected);
                    }
                } else {
                    Toast.makeText(context, "亲,您还没有登录哦", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ShowBean> list) {
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
        ImageView collection_image;
    }

    private void doHttpYes(final int id, final int i, final ViewHolder viewHolder) {
        Map map = new HashMap();
        map.put("cinemaId", id);
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/cinema/v1/verify/followCinema", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("关注成功")) {
                    Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                    list.get(i).setFollowCinema(true);
                    notifyDataSetChanged();
                } else if (data.contains("不能重复关注")) {
                    doHttpNo(id, i, viewHolder);
                }
            }

            @Override

            public void fail(String error) {

            }
        });
    }

    private void doHttpNo(int id, final int i, final ViewHolder viewHolder) {

        Map map = new HashMap();
        map.put("cinemaId", id);
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/cinema/v1/verify/cancelFollowCinema", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("取消关注成功")) {
                    Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                    list.get(i).setFollowCinema(false);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}


