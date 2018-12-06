package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CommentItemBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class UserNameCommentAdapter extends RecyclerView.Adapter<UserNameCommentAdapter.MyViewHolder>{
    private final Context context;
    private List<CommentItemBean.ResultBean> list = new ArrayList<>();

    public UserNameCommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.comment_recycle_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.simpleDraweeView = view.findViewById(R.id.image_movie_comment_activity_item);
        myViewHolder.evaluate_username_movie_item = view.findViewById(R.id.evaluate_username_movie_item);
        myViewHolder.username_evaluate_item = view.findViewById(R.id.username_evaluate_item);
        myViewHolder.time_movie_item = view.findViewById(R.id.time_movie_item);
        myViewHolder.news_text_item = view.findViewById(R.id.news_text_item);
        myViewHolder.news_image_item = view.findViewById(R.id.news_image_item);
        myViewHolder.good_text_item = view.findViewById(R.id.good_text_item);
        myViewHolder.good_image_item = view.findViewById(R.id.good_image_item);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserNameCommentAdapter.MyViewHolder myViewHolder, int i) {
        //用户头像
        myViewHolder.simpleDraweeView.setImageURI(list.get(i).getReplyHeadPic());
        //用户名称
        myViewHolder.evaluate_username_movie_item.setText(list.get(i).getReplyUserName());
        //用户评论
        myViewHolder.username_evaluate_item.setText(list.get(i).getReplyContent());
        //评论时间
        long commentTime = list.get(i).getCommentTime();
        String format = DateUtils.format(commentTime, "yyyy-MM-dd");
        myViewHolder.time_movie_item.setText(format);
        //点赞数量
        myViewHolder.good_text_item.setText(666+"");
        //评论数量
        myViewHolder.news_text_item.setText(""+666);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CommentItemBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        SimpleDraweeView simpleDraweeView;
        TextView evaluate_username_movie_item;
        TextView username_evaluate_item;
        TextView time_movie_item;
        TextView news_text_item;
        ImageView news_image_item;
        TextView good_text_item;
        ImageView good_image_item;
    }
}
