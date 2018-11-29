package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.ShowFindActivity;
import com.bw.movie.adapter.CinemaChildAdapter;
import com.bw.movie.mvp.model.RecommendBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowFindActivityPresenter extends AppDelegate implements View.OnClickListener{

    private CinemaChildAdapter adapter;
    private ImageView blank;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_find;
    }

    @Override
    public void initData() {
        super.initData();
        blank = get(R.id.blank);
        XRecyclerView xRecyclerView = get(R.id.show_find_xrecycler);
        setClick(this,R.id.blank);
        Intent intent = ((ShowFindActivity) context).getIntent();
        String find = intent.getStringExtra("find");
        adapter = new CinemaChildAdapter();
        adapter.setContext(context);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setAdapter(adapter);
        doHttp(find);
    }

    private void doHttp(String find) {
        Map<String, String> map = new HashMap<>();
        map.put("cinemaName", find);
        map.put("page", "1");
        map.put("count", "60");
        new HttpHelper().get("/movieApi/cinema/v1/findAllCinemas", map).result(new HttpListener() {
            @Override
            public void success(String data) {
//                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                RecommendBean recommendBean = new Gson().fromJson(data, RecommendBean.class);
                List<RecommendBean.ResultBean> result = recommendBean.getResult();
                adapter.setList(result);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blank:
                ((ShowFindActivity)context).finish();
                break;
        }
    }
}
