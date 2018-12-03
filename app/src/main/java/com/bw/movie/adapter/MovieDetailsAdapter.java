package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;

public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<String> list;

    public MovieDetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycle_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.movie_name_preson = view.findViewById(R.id.movie_name_preson);
        myViewHolder.movie_play = view.findViewById(R.id.movie_play);
        myViewHolder.movie_play_name = view.findViewById(R.id.movie_play_name);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.movie_name_preson.setText(list.get(i));
        myViewHolder.movie_play_name.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView movie_name_preson;
        TextView movie_play;
        TextView movie_play_name;
    }
}
