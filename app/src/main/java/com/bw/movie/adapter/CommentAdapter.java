package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.CommentBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        viewHolder.good = view.findViewById(R.id.good);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.head_logo.setImageURI(list.get(i).getCommentHeadPic());
        viewHolder.name.setText(list.get(i).getCommentUserName());
        viewHolder.mean.setText(list.get(i).getCommentContent());
        viewHolder.praise_num.setText(list.get(i).getGreatNum() + "");
        long commentTime = list.get(i).getCommentTime();
        String format = DateUtils.format(commentTime, "MM-dd HH:mm");
        viewHolder.date.setText(format);
        int isGreat = list.get(i).getIsGreat();
        final int commentId = list.get(i).getCommentId();

        viewHolder.good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                if (!TextUtils.isEmpty(sessionId)) {
                    doHttp(commentId, userId, sessionId, viewHolder, i);
                } else {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void doHttp(int commentId, int userId, String sessionId, final ViewHolder viewHolder, final int i) {
        ///movieApi/cinema/v1/verify/cinemaCommentGreat

        Map map = new HashMap();
        map.put("commentId", commentId);
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "/movieApi/cinema/v1/verify/cinemaCommentGreat", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("点赞成功")) {
                    Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
                    list.get(i).setIsGreat(1);
                    viewHolder.good.setImageResource(R.mipmap.praise_selected);
                    viewHolder.praise_num.setText(list.get(i).getGreatNum() + 1 + "");
                } else {
                    Toast.makeText(context, "您已点赞", Toast.LENGTH_SHORT).show();
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
        ImageView good;
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
