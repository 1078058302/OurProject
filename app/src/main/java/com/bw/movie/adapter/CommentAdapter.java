package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CommentBean;
import com.bw.movie.utils.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends XRecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CommentBean.ResultBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.comment_xre_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.head_logo = view.findViewById(R.id.head_logo);
        viewHolder.name = view.findViewById(R.id.name);
        viewHolder.mean = view.findViewById(R.id.mean_comment);
        viewHolder.date = view.findViewById(R.id.date);
        viewHolder.praise_num = view.findViewById(R.id.praise_num);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.head_logo.setImageURI(list.get(i).getCommentHeadPic());
        viewHolder.name.setText(list.get(i).getCommentUserName());
        viewHolder.mean.setText(list.get(i).getCommentContent());
        viewHolder.praise_num.setText(list.get(i).getGreatNum() + "");
        long commentTime = list.get(i).getCommentTime();
        String format = DateUtils.format(commentTime, "MM-dd HH:mm");
        viewHolder.date.setText(format);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CommentBean.ResultBean> list) {
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

        SimpleDraweeView head_logo;
        TextView name;
        TextView mean;
        TextView date;
        TextView praise_num;
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);

    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}
