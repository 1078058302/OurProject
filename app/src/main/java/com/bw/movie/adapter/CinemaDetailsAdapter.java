package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
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

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cinema_name.setText(list.get(i).getScreeningHall());
        viewHolder.start_time.setText(list.get(i).getBeginTime() + "");
        viewHolder.end_time.setText(list.get(i).getEndTime() + "");
        double price = list.get(i).getPrice();
        String s = doubleToString(price);
        viewHolder.price.setText(30 + i + "");



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
