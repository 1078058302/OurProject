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
import com.bw.movie.activity.CommentActivity;
import com.bw.movie.mvp.model.CollectonBena;
import com.bw.movie.mvp.model.EvaluateBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieEvaluateAdapter extends RecyclerView.Adapter<MovieEvaluateAdapter.MyViewHolder> {
    private Context context;
    private List<EvaluateBean.ResultBean> list = new ArrayList<>();
    private int commentId;

    public MovieEvaluateAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieEvaluateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.evaluate_recycle_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.evaluate_image_movie = view.findViewById(R.id.evaluate_image_movie);
        myViewHolder.evaluate_username_movie = view.findViewById(R.id.evaluate_username_movie);
        myViewHolder.evaluate_username_evaluate = view.findViewById(R.id.evaluate_username_evaluate);
        myViewHolder.evaluate_time_movie = view.findViewById(R.id.evaluate_time_movie);
        myViewHolder.evaluate_good_text = view.findViewById(R.id.evaluate_good_text);
        myViewHolder.evaluate_good_image = view.findViewById(R.id.evaluate_good_image);
        myViewHolder.evaluate_news_text = view.findViewById(R.id.evaluate_news_text);
        myViewHolder.evaluate_news_image = view.findViewById(R.id.evaluate_news_image);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieEvaluateAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.evaluate_image_movie.setImageURI(list.get(i).getCommentHeadPic());
        myViewHolder.evaluate_username_movie.setText(list.get(i).getCommentUserName());
        myViewHolder.evaluate_username_evaluate.setText(list.get(i).getCommentContent());
        long commentTime = list.get(i).getCommentTime();
        String format = DateUtils.format(commentTime, "yyyy-MM-dd-HH:mm:ss");
        myViewHolder.evaluate_time_movie.setText(format);
        myViewHolder.evaluate_good_text.setText(list.get(i).getGreatNum() + "");
        myViewHolder.evaluate_news_text.setText(list.get(i).getReplyNum() + "");
        final int commentId = list.get(i).getCommentId();
        SharedPreferencesUtils.putInt(context, "commentId", commentId);
        myViewHolder.evaluate_news_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CommentActivity.class));
            }
        });
        myViewHolder.evaluate_news_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CommentActivity.class));
            }
        });
        int isGreat = list.get(i).getIsGreat();
        if (isGreat == 0) {
            myViewHolder.evaluate_good_image.setImageResource(R.mipmap.praise_default);
        } else {
            myViewHolder.evaluate_good_image.setImageResource(R.mipmap.praise_selected);
        }
        myViewHolder.evaluate_good_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isGreat = list.get(i).getIsGreat();
                if (isGreat == 0) {
                    doEvaluateHttp(commentId, myViewHolder, i);
                }
            }
        });

    }

    private void doEvaluateHttp(int commentId, final MyViewHolder myViewHolder, final int i) {
        final Map map = new HashMap<>();
        map.put("commentId", commentId);
        Map<String, String> mapHead = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "movieApi/movie/v1/verify/movieCommentGreat", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                CollectonBena collectonBena = new Gson().fromJson(data, CollectonBena.class);
                if (collectonBena.getStatus() == "0000") {
                    Toast.makeText(context, "" + collectonBena.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "" + collectonBena.getMessage(), Toast.LENGTH_SHORT).show();
                    myViewHolder.evaluate_good_image.setImageResource(R.mipmap.praise_selected);
                    int greatNum = list.get(i).getGreatNum();
                    greatNum++;
                    myViewHolder.evaluate_good_text.setText(greatNum + "");
                }


            }

            @Override
            public void fail(String error) {

            }
        });
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
        TextView evaluate_time_movie;
        TextView evaluate_good_text;
        ImageView evaluate_good_image;
        ImageView evaluate_news_image;
        TextView evaluate_news_text;
    }
}
