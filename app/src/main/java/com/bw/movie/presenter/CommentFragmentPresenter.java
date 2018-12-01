package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CommentAdapter;
import com.bw.movie.mvp.model.CommentBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentFragmentPresenter extends AppDelegate {

    private CommentAdapter adapter;
    private XRecyclerView xRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.comment_layout;
    }

    @Override
    public void initData() {
        super.initData();
        //http://172.17.8.100/movieApi/cinema/v1/findAllCinemaComment?page=1&count=60&cinemaId=2
        int cinemaId = SharedPreferencesUtils.getInt(context, "cinemaId");
        xRecyclerView = get(R.id.comment_xrecycler);
        adapter = new CommentAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setAdapter(adapter);
        doHttp(cinemaId);
    }

    private void doHttp(int cinemaId) {
        Map map = new HashMap();
        map.put("page", 1);
        map.put("count", 100);
        map.put("cinemaId", cinemaId);
        new HttpHelper().get("/movieApi/cinema/v1/findAllCinemaComment", map).result(new HttpListener() {
            @Override
            public void success(String data) {
//                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                CommentBean commentBean = new Gson().fromJson(data, CommentBean.class);
                List<CommentBean.ResultBean> result = commentBean.getResult();
                adapter.setList(result);
                adapter.setContext(context);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
