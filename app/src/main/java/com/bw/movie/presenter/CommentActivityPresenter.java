package com.bw.movie.presenter;

import android.content.Context;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

public class CommentActivityPresenter extends AppDelegate {
    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        doCommentHttp();
    }

    private void doCommentHttp() {

        int commentId = SharedPreferencesUtils.getInt(context, "commentId");
        Map<String, String> map = new HashMap<>();
        map.put("commentId", commentId + "");
        map.put("page", 1 + "");
        map.put("count", 5 + "");

        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map mapHead = new HashMap<>();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("movieApi/movie/v1/findCommentReply", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                Toast.makeText(context, "result" + data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
