package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.EvaluateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MovieEvaluateAdapter extends RecyclerView.Adapter<MovieEvaluateAdapter.MyViewHolder> {
    private  Context context;
    private List<EvaluateBean.ResultBean> list = new ArrayList<>();

    public MovieEvaluateAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieEvaluateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("TAG", "onCreateViewHolder的集合" + list.size());
        View view = View.inflate(context, R.layout.evaluate_recycle_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.evaluate_image_movie = view.findViewById(R.id.evaluate_image_movie);
        myViewHolder.evaluate_username_movie = view.findViewById(R.id.evaluate_username_movie);
        myViewHolder.evaluate_username_evaluate = view.findViewById(R.id.evaluate_username_evaluate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieEvaluateAdapter.MyViewHolder myViewHolder, int i) {
        Log.i("TAG", "onBindViewHolder的集合" + list.size());
        Log.i("TAG", list.get(i).getCommentHeadPic() + "图片");
        Log.i("TAG", list.get(i).getCommentUserName() + "昵称");
        Log.i("TAG", list.get(i).getCommentContent() + "评论");
        myViewHolder.evaluate_image_movie.setImageURI(list.get(i).getCommentHeadPic());
        myViewHolder.evaluate_username_movie.setText(list.get(i).getCommentUserName());
        myViewHolder.evaluate_username_evaluate.setText(list.get(i).getCommentContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<EvaluateBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        SimpleDraweeView evaluate_image_movie;
        TextView evaluate_username_movie;
        TextView evaluate_username_evaluate;
    }
}
