package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaCityActivity;
import com.bw.movie.mvp.model.FilmCinemaBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmCinemaAdapter extends RecyclerView.Adapter<FilmCinemaAdapter.MyViewHolder> {

    private Context context;
    private List<FilmCinemaBean.ResultBean> list = new ArrayList<>();
    private String sessionId;
    private int userId;
    private int id;
    public boolean b = true;

    public FilmCinemaAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_show_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.cinema_image.setImageURI(list.get(i).getLogo());
        myViewHolder.title_cinema.setText(list.get(i).getName());
        myViewHolder.desc_cinema.setText(list.get(i).getAddress());

        if (list.get(i).getName().contains("院")) {
            myViewHolder.away_cinema.setText("3.2" + "km");
        } else {
            myViewHolder.away_cinema.setText("1.6" + "km");
        }


        sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        userId = SharedPreferencesUtils.getInt(context, "userId");
        if (i != 0) {
            id = list.get(i - 1).getId();
        }
        boolean followCinema = list.get(i).isFollowCinema();
        if (followCinema) {
            myViewHolder.collection_image.setImageResource(R.mipmap.collection_default);
        } else {
            myViewHolder.collection_image.setImageResource(R.mipmap.collection_selected);
        }


        myViewHolder.cinema_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CinemaCityActivity.class);
                intent.putExtra("name", list.get(i).getName())
                        .putExtra("address", list.get(i).getAddress())
                        .putExtra("logo", list.get(i).getLogo())
                        .putExtra("cinemaid", list.get(i).getId());

                context.startActivity(intent);
            }
        });

        myViewHolder.collection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sessionId)) {
                    if (b) {
                        doHttpYes(id, i, myViewHolder);
                        b = false;
                    } else {
                        doHttpNo(id, i, myViewHolder);
                        b = true;
                    }
                } else {
                    Toast.makeText(context, "亲,您还没有登录哦", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    private void doHttpNo(int id, final int i, final MyViewHolder myViewHolder) {
        Toast.makeText(context, userId + "" + sessionId, Toast.LENGTH_SHORT).show();
        Map map = new HashMap();
        map.put("cinemaId", id);
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/cinema/v1/verify/cancelFollowCinema", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                list.get(i).setFollowCinema(true);
                myViewHolder.collection_image.setImageResource(R.mipmap.collection_default);
            }

            @Override
            public void fail(String error) {

            }
        });
    }


    private void doHttpYes(int id, final int i, final MyViewHolder myViewHolder) {
        Map map = new HashMap();
        map.put("cinemaId", id);
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("/movieApi/cinema/v1/verify/followCinema", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                list.get(i).setFollowCinema(false);
                myViewHolder.collection_image.setImageResource(R.mipmap.collection_selected);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<FilmCinemaBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cinema_image;
        TextView title_cinema;
        TextView desc_cinema;
        TextView away_cinema;
        LinearLayout cinema_show;
        ImageView collection_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cinema_image = itemView.findViewById(R.id.cinema_image);
            title_cinema = itemView.findViewById(R.id.title_cinema);
            desc_cinema = itemView.findViewById(R.id.desc_cinema);
            away_cinema = itemView.findViewById(R.id.away_cinema);
            cinema_show = itemView.findViewById(R.id.cinema_show);
            collection_image = itemView.findViewById(R.id.collection_image);
        }
    }
}
