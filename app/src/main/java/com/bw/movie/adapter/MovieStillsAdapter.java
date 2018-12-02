package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

public class MovieStillsAdapter extends RecyclerView.Adapter<MovieStillsAdapter.MyViewHolder> {
    private final Context context;
    private List<String> list = new ArrayList<>();

    public MovieStillsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieStillsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.stills_recycle_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.stillsimage = view.findViewById(R.id.stills_item_image);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieStillsAdapter.MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(list.get(i)).into(myViewHolder.stillsimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        ImageView stillsimage;
    }
}
