package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.mvp.model.NextMovieBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/*
 * insert zhang
 */
public class NextMovieAdapter extends RecyclerView.Adapter<NextMovieAdapter.MyViewHolder> {

    private List<NextMovieBean.ResultBean> resultnext = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public NextMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.hotmovie_recycle_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.simpleDraweeView = view.findViewById(R.id.hotmovie_item_image);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull NextMovieAdapter.MyViewHolder myViewHolder, int i) {
        String imageUrl = resultnext.get(i).getImageUrl();
        myViewHolder.simpleDraweeView.setImageURI(imageUrl);
    }

    @Override
    public int getItemCount() {
        return resultnext.size();
    }

    public void setList(List<NextMovieBean.ResultBean> resultnext, Context context) {
        this.resultnext = resultnext;
        this.context = context;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        SimpleDraweeView simpleDraweeView;
    }
}
