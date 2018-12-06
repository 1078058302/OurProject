package com.bw.movie.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.GuanZhuBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void onBindViewHolder(@NonNull FilmAdapter.MyViewHolder myViewHolder, final int i) {
        GuanZhuBean.ResultBean resultBean = result.get(i);
        myViewHolder.sd_photo.setImageURI(resultBean.getImageUrl());
        myViewHolder.tv_filmName.setText(resultBean.getName());
        myViewHolder.tv_filmContent.setText(resultBean.getSummary());
        //转换日期格式
        long releaseTime = resultBean.getReleaseTime();
        String format = DateUtils.format(releaseTime, "yyyy-MM-dd HH:mm:ss");
        myViewHolder.tv_date.setText(format);
        //长安点击事件
        myViewHolder.rl_layout2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //获取id
                final int id = result.get(i).getId();
                final int userId = SharedPreferencesUtils.getInt(context, "userId");
                final String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                //alertdialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("是否确定取消关注");
                //点击对话框以外的区域是否让对话框消失
                builder.setCancelable(true);
                //确定
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        Map<String, String> map = new HashMap<>();
                        map.put("movieId", id + "");
                        Map<String, String> mapHead = new HashMap<>();
                        mapHead.put("userId", userId + "");
                        mapHead.put("sessionId", sessionId);
                        new HttpHelper().getHead("/movieApi/movie/v1/verify/cancelFollowMovie", map, mapHead).result(new HttpListener() {
                            @Override
                            public void success(String data) {
                                if (data.contains("取消关注成功")) {
                                    Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                                    listener.setItem();
                                }
                            }

                            @Override
                            public void fail(String error) {
                                Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                //取消
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                //显示
                builder.show();

                return false;
            }

        });
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
        private RelativeLayout rl_layout2;
        private TextView tv_date;
        private TextView tv_filmName, tv_filmContent;
        private SimpleDraweeView sd_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sd_photo = (SimpleDraweeView) itemView.findViewById(R.id.sd_photo);
            tv_filmName = (TextView) itemView.findViewById(R.id.tv_filmName);
            tv_filmContent = (TextView) itemView.findViewById(R.id.tv_filmContent);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            rl_layout2 = (RelativeLayout) itemView.findViewById(R.id.rl_layout2);
        }
    }
    //接口回调

    private CineamsAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(CineamsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void setItem();

    }
}
