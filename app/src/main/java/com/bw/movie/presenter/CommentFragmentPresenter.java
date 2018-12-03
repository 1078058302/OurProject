package com.bw.movie.presenter;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
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

public class CommentFragmentPresenter extends AppDelegate{

    private CommentAdapter adapter;
    private XRecyclerView xRecyclerView;
    private EditText ping;
    private TextView sure_ping;
    private Window window;
    private RelativeLayout mLayoutEdit;
    private int windowHeight;

    @Override
    public int getLayoutId() {
        return R.layout.comment_layout;
    }

    @Override
    public void initData() {
        super.initData();
        //http://172.17.8.100/movieApi/cinema/v1/findAllCinemaComment?page=1&count=60&cinemaId=2
        final int cinemaId = SharedPreferencesUtils.getInt(context, "cinemaId");
        xRecyclerView = get(R.id.comment_xrecycler);
        mLayoutEdit = (RelativeLayout) get(R.id.submit);
        DisplayMetrics dm = new DisplayMetrics();
        windowHeight = dm.heightPixels;

        ping = get(R.id.ping);
        sure_ping = get(R.id.sure_ping);
        adapter = new CommentAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setAdapter(adapter);
        doHttp(cinemaId);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttp(cinemaId);
            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
        sure_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = ping.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(context, "请输入您的评论", Toast.LENGTH_SHORT).show();
                } else {

                    doPing(cinemaId, trim);
                }
            }
        });
//        ping.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                } else {
////                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                }
//
//            }
//        });
    }

    private void doPing(final int cinemaId, String trim) {
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        Map mapHead = new HashMap();
        Map map = new HashMap();
        map.put("cinemaId", cinemaId);
        map.put("commentContent", trim);
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "/movieApi/cinema/v1/verify/cinemaComment", map).result(new HttpListener() {
            @Override
            public void success(String data) {
//                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                if (data.contains("评论成功")) {
                    Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
//                    adapter.notifyDataSetChanged();
                    doHttp(cinemaId);
                    ping.setText("");
                }
            }

            @Override
            public void fail(String error) {

            }
        });
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
                xRecyclerView.refreshComplete();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getWindow(Window window) {
        this.window = window;
    }
//
//    @Override
//    public void onLayoutChange(View v, int left, int top, int right,
//                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
//        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > windowHeight / 3)) {
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLayoutEdit.getLayoutParams();
//            params.bottomMargin = windowHeight / 3;
//            mLayoutEdit.setLayoutParams(params);
//
//        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > windowHeight / 3)) {
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLayoutEdit.getLayoutParams();
//            params.bottomMargin = 0;
//            mLayoutEdit.setLayoutParams(params);
//
//        }
//    }
}
