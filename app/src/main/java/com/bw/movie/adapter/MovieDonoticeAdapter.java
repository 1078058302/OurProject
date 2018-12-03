package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.mvp.model.MovieDescBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

public class MovieDonoticeAdapter extends RecyclerView.Adapter<MovieDonoticeAdapter.MyViewHolder> {
    private final Context context;
    private List<MovieDescBean.ResultBean.ShortFilmListBean> list = new ArrayList<>();

    public MovieDonoticeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieDonoticeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.donotice_recycle_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.mJC = view.findViewById(R.id.mJC);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDonoticeAdapter.MyViewHolder myViewHolder, int i) {
        String videoUrl = list.get(i).getVideoUrl();
        String imageUrl = list.get(i).getImageUrl();
        myViewHolder.mJC.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        myViewHolder.mJC.TOOL_BAR_EXIST = false;
        myViewHolder.mJC.setUp(videoUrl, JZVideoPlayerStandard.SCROLL_AXIS_HORIZONTAL, "这视频真好，去除黑边了");
        Glide.with(context).load(imageUrl).into(myViewHolder.mJC.thumbImageView);
        myViewHolder.mJC.widthRatio = 4;
        myViewHolder.mJC.heightRatio = 3;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<MovieDescBean.ResultBean.ShortFilmListBean> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        JZVideoPlayerStandard mJC;
    }
}
