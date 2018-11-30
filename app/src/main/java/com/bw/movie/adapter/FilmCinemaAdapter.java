package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaCityActivity;
import com.bw.movie.mvp.model.FilmCinemaBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FilmCinemaAdapter extends RecyclerView.Adapter<FilmCinemaAdapter.MyViewHolder> {

    private Context context;
    private List<FilmCinemaBean.ResultBean> list = new ArrayList<>();

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.cinema_image.setImageURI(list.get(i).getLogo());
        myViewHolder.title_cinema.setText(list.get(i).getName());
        myViewHolder.desc_cinema.setText(list.get(i).getAddress());
        myViewHolder.away_cinema.setText("0");
        myViewHolder.cinema_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CinemaCityActivity.class);
                intent.putExtra("name",list.get(i).getName())
                        .putExtra("address",list.get(i).getAddress())
                        .putExtra("logo",list.get(i).getLogo());

                context.startActivity(intent);
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cinema_image = itemView.findViewById(R.id.cinema_image);
            title_cinema = itemView.findViewById(R.id.title_cinema);
            desc_cinema = itemView.findViewById(R.id.desc_cinema);
            away_cinema = itemView.findViewById(R.id.away_cinema);
            cinema_show=itemView.findViewById(R.id.cinema_show);
        }
    }
}
