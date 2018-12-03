package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.mvp.model.HotMovieBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class HotMovieShowAdapter extends RecyclerView.Adapter<HotMovieShowAdapter.MyViewHolder> {
    private List<HotMovieBean.ResultBean> result = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public HotMovieShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.movieshow_movie_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.image_movie = view.findViewById(R.id.movieshow_image_movie);
        myViewHolder.text_title = view.findViewById(R.id.movieshow_text_title);
        myViewHolder.text_desc = view.findViewById(R.id.movieshow_text_desc);
        myViewHolder.movie_show_Collection = view.findViewById(R.id.movie_show_Collection);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HotMovieShowAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.text_title.setText(result.get(i).getName());
        myViewHolder.text_desc.setText("简介:" + result.get(i).getSummary());
        String imageUrl = result.get(i).getImageUrl();
        if(result.get(i).isFollowMovie()){
            myViewHolder.movie_show_Collection.setImageResource(R.mipmap.collection_selected);
        }else{
            myViewHolder.movie_show_Collection.setImageResource(R.mipmap.collection_default);
        }
        myViewHolder.image_movie.setImageURI(imageUrl);
        myViewHolder.image_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movie_id = result.get(i).getId();
                Intent intent = new Intent(context, MovieDescActivity.class);
                intent.putExtra("movie_id", movie_id);
                context.startActivity(intent);
            }
        });

        myViewHolder.movie_show_Collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean followMovie = result.get(i).isFollowMovie();
                Toast.makeText(context, ""+followMovie, Toast.LENGTH_SHORT).show();
                if (followMovie) {
                    result.get(i).setCheck(false);
                } else {
                    result.get(i).setCheck(true);
                }
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setlist(Context context, List<HotMovieBean.ResultBean> result) {
        this.context = context;
        this.result = result;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        SimpleDraweeView image_movie;
        TextView text_title;
        TextView text_desc;
        ImageView movie_show_Collection;
    }
}
