package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.GuanZhu2Bean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CineamsAdapter extends RecyclerView.Adapter<CineamsAdapter.MyViewHolder> {
    private Context context;
    private List<GuanZhu2Bean.ResultBean> result = new ArrayList<>();

    public CineamsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CineamsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_cineams, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CineamsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.sd_photo2.setImageURI(result.get(i).getLogo());
        myViewHolder.tv_filmName2.setText(result.get(i).getName());
        myViewHolder.tv_filmContent2.setText(result.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setList(List<GuanZhu2Bean.ResultBean> list) {
        this.result = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_filmName2, tv_filmContent2;
        private SimpleDraweeView sd_photo2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sd_photo2 = (SimpleDraweeView) itemView.findViewById(R.id.sd_photo2);
            tv_filmName2 = (TextView) itemView.findViewById(R.id.tv_filmName2);
            tv_filmContent2 = (TextView) itemView.findViewById(R.id.tv_filmContent2);
        }
    }
}
