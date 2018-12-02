package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.GuanZhuBean;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {
    private Context context;
    private List<GuanZhuBean.ResultBean> result = new ArrayList<>();

    public FilmAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FilmAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.layout_film, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.MyViewHolder myViewHolder, int i) {
        GuanZhuBean.ResultBean resultBean = result.get(i);
        myViewHolder.sd_photo.setImageURI(resultBean.getImageUrl());
        myViewHolder.tv_filmName.setText(resultBean.getName());
        myViewHolder.tv_filmContent.setText(resultBean.getSummary());
        myViewHolder.tv_date.setText((int) resultBean.getReleaseTime());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setList(List<GuanZhuBean.ResultBean> list) {
        this.result = list;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private TextView tv_filmName, tv_filmContent;
        private SimpleDraweeView sd_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sd_photo = (SimpleDraweeView) itemView.findViewById(R.id.sd_photo);
            tv_filmName = (TextView) itemView.findViewById(R.id.tv_filmName);
            tv_filmContent = (TextView) itemView.findViewById(R.id.tv_filmContent);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
