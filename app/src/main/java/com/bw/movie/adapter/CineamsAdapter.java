package com.bw.movie.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.GuanZhu2Bean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void onBindViewHolder(@NonNull CineamsAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.sd_photo2.setImageURI(result.get(i).getLogo());
        myViewHolder.tv_filmName2.setText(result.get(i).getName());
        myViewHolder.tv_filmContent2.setText(result.get(i).getAddress());

        //长按点击
        myViewHolder.rl_layout.setOnLongClickListener(new View.OnLongClickListener() {
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
                        map.put("cinemaId", id + "");
                        Map<String, String> mapHead = new HashMap<>();
                        mapHead.put("userId", userId + "");
                        mapHead.put("sessionId", sessionId);
                        new HttpHelper().getHead("/movieApi/cinema/v1/verify/cancelFollowCinema", map, mapHead).result(new HttpListener() {
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

    public void setList(List<GuanZhu2Bean.ResultBean> list) {
        this.result = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rl_layout;
        private TextView tv_filmName2, tv_filmContent2;
        private SimpleDraweeView sd_photo2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sd_photo2 = (SimpleDraweeView) itemView.findViewById(R.id.sd_photo2);
            tv_filmName2 = (TextView) itemView.findViewById(R.id.tv_filmName2);
            tv_filmContent2 = (TextView) itemView.findViewById(R.id.tv_filmContent2);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);

        }
    }
    //接口回调

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void setItem();

    }
}
