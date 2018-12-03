package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.BuyTicketActivity;
import com.bw.movie.mvp.model.CinemaDetailsBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CinemaDetailsAdapter extends XRecyclerView.Adapter<CinemaDetailsAdapter.ViewHolder> {
    private List<CinemaDetailsBean.ResultBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_details_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cinema_name = view.findViewById(R.id.cinema_name_de);
        viewHolder.start_time = view.findViewById(R.id.start_time);
        viewHolder.end_time = view.findViewById(R.id.end_time);
        viewHolder.price = view.findViewById(R.id.price);
        viewHolder.jump = view.findViewById(R.id.jump);
        viewHolder.details_layout = view.findViewById(R.id.details_layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.cinema_name.setText(list.get(i).getScreeningHall());
        viewHolder.start_time.setText(list.get(i).getBeginTime() + "");
        viewHolder.end_time.setText(list.get(i).getEndTime() + "");
        double price = list.get(i).getPrice();
        String s = doubleToString(price);
        final int price_one = 30 + i;
        viewHolder.price.setText(price_one + "");

        viewHolder.details_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String beginTime = list.get(i).getBeginTime();
                String endTime = list.get(i).getEndTime();
                int seatsTotal = list.get(i).getSeatsTotal();
                int status = list.get(i).getId();
                int seatsUseCount = list.get(i).getSeatsUseCount();
                String screeningHall = list.get(i).getScreeningHall();
                String duration = list.get(i).getDuration();
                Intent intent = new Intent(context, BuyTicketActivity.class);
                intent.putExtra("beginTime", beginTime)
                        .putExtra("endTime", endTime)
                        .putExtra("seatsTotal", seatsTotal)
                        .putExtra("seatsUseCount", seatsUseCount)
                        .putExtra("screeningHall", screeningHall)
                        .putExtra("duration", duration)
                        .putExtra("price", price_one)
                        .putExtra("status", status);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CinemaDetailsBean.ResultBean> list) {

        this.list = list;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView cinema_name;
        TextView start_time;
        TextView end_time;
        TextView price;
        ImageView jump;
        LinearLayout details_layout;
    }

    /**
     * double转String,保留小数点后两位
     *
     * @param num
     * @return
     */
    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


}
