package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.model.BannerBean;
import com.bw.movie.mvp.model.MovieingBean;

import java.util.ArrayList;
import java.util.List;

public class PageMovieingAdapter extends RecyclerView.Adapter<PageMovieingAdapter.ViewHolder> {
    private List<MovieingBean.ResultBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_main1, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.imageView = view.findViewById(R.id.iv);
        viewHolder.movie_name = view.findViewById(R.id.movie_name);
        viewHolder.time = view.findViewById(R.id.time);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).load(list.get(i).getImageUrl()).into(viewHolder.imageView);
        viewHolder.movie_name.setText(list.get(i).getName());
        viewHolder.time.setText(list.get(i).getDuration());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backId(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<MovieingBean.ResultBean> list) {
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

        ImageView imageView;
        TextView movie_name;
        TextView time;
    }

    private PageListener listener;

    public void result(PageListener listener) {
        this.listener = listener;
    }


    public interface PageListener {
        void backId(int i);
    }
}
