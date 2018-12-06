package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.model.TicketRecordBean;
import com.bw.movie.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class TicketRecordAdapter extends RecyclerView.Adapter<TicketRecordAdapter.MyViewHolder> {
    private Context context;
    private List<TicketRecordBean.ResultBean> result = new ArrayList<>();

    public TicketRecordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TicketRecordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_ticketrecord, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketRecordAdapter.MyViewHolder myViewHolder, int i) {
        long createTime = result.get(i).getCreateTime();
        String format = DateUtils.format(createTime, "MM-dd HH:mm");
        myViewHolder.bt_createTime.setText(format);
        myViewHolder.tv_movieName.setText(result.get(i).getMovieName());
        myViewHolder.tv_orderId.setText("订单号:" + result.get(i).getOrderId());
        myViewHolder.tv_cineamName.setText("影院:" + result.get(i).getCinemaName());
        myViewHolder.tv_screeningHall.setText("影厅:" + result.get(i).getScreeningHall());
        String beginTime = result.get(i).getBeginTime();
        myViewHolder.tv_beginname.setText("时间:" + beginTime);
        String endTime = result.get(i).getEndTime();
        myViewHolder.tv_endName.setText("-" + endTime);
        myViewHolder.tv_amount.setText("数量:" + result.get(i).getAmount() + "张" + "");
        myViewHolder.tv_price.setText("金额:" + result.get(i).getPrice() + "元" + "");
        int status = result.get(i).getStatus();
        if (status == 1) {
            myViewHolder.bt_status.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.bt_status.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setList(List<TicketRecordBean.ResultBean> list) {
        this.result = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_movieName, tv_orderId, tv_cineamName,
                tv_screeningHall, tv_beginname, tv_endName, tv_amount, tv_price;
        private Button bt_createTime, bt_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_createTime = (Button) itemView.findViewById(R.id.bt_createTime);
            tv_movieName = (TextView) itemView.findViewById(R.id.tv_movieName);
            tv_orderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            tv_cineamName = (TextView) itemView.findViewById(R.id.tv_cineamName);
            tv_screeningHall = (TextView) itemView.findViewById(R.id.tv_screeningHall);
            tv_beginname = (TextView) itemView.findViewById(R.id.tv_beginname);
            tv_endName = (TextView) itemView.findViewById(R.id.tv_endName);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            bt_status = (Button) itemView.findViewById(R.id.bt_status);
        }
    }
}
