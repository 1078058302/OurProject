package com.bw.movie.presenter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
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

public class CommentFragmentPresenter extends AppDelegate {

    private CommentAdapter adapter;
    private XRecyclerView xRecyclerView;
    private EditText ping;
    private TextView sure_ping;
    private View mContentView;

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
        DisplayMetrics dm = new DisplayMetrics();
        mContentView = get(R.id.contentView);

        ping = get(R.id.ping);
        sure_ping = get(R.id.sure_ping);
        buttonBeyondKeyboardLayout(mContentView, sure_ping);
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
                if (data.contains("评论成功")) {
                    Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
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

    private void buttonBeyondKeyboardLayout(final View root, final View button) {
        // 监听根布局的视图变化
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取内容布局在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 获取内容布局在窗体的不可视区域高度(被其他View遮挡的区域高度)
                        int rootInvisibleHeight = root.getHeight() - rect.bottom;
                        // 若不可视区域高度大于100，则键盘显示
                        if (rootInvisibleHeight > 100) {
                            int[] location = new int[2];
                            // 获取须顶上去的控件在窗体的坐标
                            button.getLocationInWindow(location);
                            // 计算内容滚动高度，使button在可见区域
                            int buttonHeight = (location[1]
                                    + button.getHeight()) - rect.bottom;
                            root.scrollTo(0, buttonHeight);
                        } else {
                            // 键盘隐藏
                            root.scrollTo(0, 0);
                        }
                    }
                });
    }

}
