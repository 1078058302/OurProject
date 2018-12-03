package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.mvp.model.MovieingBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/*
 * insert zhang
 */
public class MovieingAdapter extends RecyclerView.Adapter<MovieingAdapter.MyViewHolder> {

    private List<MovieingBean.ResultBean> resulting = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public MovieingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.hotmovie_recycle_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.simpleDraweeView = view.findViewById(R.id.hotmovie_item_image);
        myViewHolder.movie_name = view.findViewById(R.id.movie_fragment_name);
        myViewHolder.movie_fragment_item_relative = view.findViewById(R.id.movie_fragment_item_relative);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MovieingAdapter.MyViewHolder myViewHolder, final int i) {
        String imageUrl = resulting.get(i).getImageUrl();
        myViewHolder.simpleDraweeView.setImageURI(imageUrl);
        myViewHolder.movie_name.setText(resulting.get(i).getName());
        myViewHolder.movie_fragment_item_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movie_id = resulting.get(i).getId();
                Intent intent = new Intent(context, MovieDescActivity.class);
                intent.putExtra("movie_id",movie_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resulting.size();
    }

    public void setList(List<MovieingBean.ResultBean> resulting, Context context) {
        this.resulting = resulting;
        this.context = context;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        SimpleDraweeView simpleDraweeView;
        TextView movie_name;
        RelativeLayout movie_fragment_item_relative;
    }
}
