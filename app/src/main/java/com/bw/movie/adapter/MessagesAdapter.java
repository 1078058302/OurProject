package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.MessagesBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private Context context;
    private List<MessagesBean.ResultBean> result = new ArrayList<>();
    public MessagesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_messages, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessagesAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_information.setText(result.get(i).getTitle());
        myViewHolder.tv_details.setText(result.get(i).getContent());
        long pushTime = result.get(i).getPushTime();
        String format = DateUtils.format(pushTime, "MM-dd HH:mm");
        myViewHolder.tv_time.setText(format);
        final int status = result.get(i).getStatus();
        if (status == 0) {
            //0未读 1 已读
            myViewHolder.tv_icon.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.tv_icon.setVisibility(View.GONE);
        }
        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                int id = result.get(i).getId();
                Map<String, String> mapHead = new HashMap<>();
                mapHead.put("userId", userId + "");
                mapHead.put("sessionId", sessionId);
                Map<String, String> map = new HashMap<>();
                map.put("id", id + "");
                new HttpHelper().getHead("/movieApi/tool/v1/verify/changeSysMsgStatus", map, mapHead).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        if (data.contains("状态改变成功") && result.get(i).getStatus()==0) {
                            listener.setRefresh();
                            Toast.makeText(context, "状态改变成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "已经是已读消息了", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void fail(String error) {
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setList(List<MessagesBean.ResultBean> list) {
        this.result = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_icon;
        private RelativeLayout relativeLayout;
        private TextView tv_information, tv_details, tv_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_information = (TextView) itemView.findViewById(R.id.tv_information);
            tv_details = (TextView) itemView.findViewById(R.id.tv_details);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_icon = (TextView) itemView.findViewById(R.id.tv_icon);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

    //接口回调
    private onRefreshClickListener listener;

    public void setOnRefreshClickListener(onRefreshClickListener listener) {
        this.listener = listener;

    }

    public interface onRefreshClickListener {
        void setRefresh();
    }
}
