package com.bw.movie.presenter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CommentActivity;
import com.bw.movie.adapter.UserNameCommentAdapter;
import com.bw.movie.mvp.model.CommentItemBean;
import com.bw.movie.mvp.model.EvaluateBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivityPresenter extends AppDelegate implements View.OnClickListener {

    private SimpleDraweeView image_movie_comment_activity;
    private TextView username;
    private TextView username_evaluate;
    private TextView time_movie;
    private TextView news_text;
    private ImageView news_image;
    private TextView good_text;
    private ImageView good_image;
    private EvaluateBean.ResultBean resultBean;
    private ImageView fanhui_01;
    private EditText ed_evaluate_two;
    private TextView te_evaluate_two;
    private int userId;
    private String sessionId;
    private int commentId;
    private XRecyclerView recycle_user_comment;
    private UserNameCommentAdapter userNameCommentAdapter ;
    private int count = 5;
    private LinearLayout linearLayout;


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

        resultBean = (EvaluateBean.ResultBean) ((CommentActivity) context).getIntent().getSerializableExtra("resultBean");
        //用户头像
        image_movie_comment_activity = get(R.id.image_movie_comment_activity);
        //用户名称
        username = get(R.id.evaluate_username_movie);
        //用户评论
        username_evaluate = get(R.id.username_evaluate);
        //评论时间
        time_movie = get(R.id.time_movie);
        //消息数量
        news_text = get(R.id.news_text);
        //消息数量图片
        news_image = get(R.id.news_image);
        //点赞数量
        good_text = get(R.id.good_text);
        //点赞图标
        good_image = get(R.id.good_image);
        //返回到上一界面
        fanhui_01 = get(R.id.fanhui_01);
        //写入的评论
        ed_evaluate_two = get(R.id.ed_evaluate_two);
        //提交评论
        te_evaluate_two = get(R.id.te_evaluate_two);
        //回复的评论recycle
        recycle_user_comment = get(R.id.recycle_user_comment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_user_comment.setLayoutManager(linearLayoutManager);
        userNameCommentAdapter = new UserNameCommentAdapter(context);
        recycle_user_comment.setAdapter(userNameCommentAdapter);
        linearLayout = get(R.id.layout_bottom);
        buttonBeyondKeyboardLayout(linearLayout, te_evaluate_two);
        ChenJinShi();
        //点击事件
        setClick(this, R.id.fanhui_01, R.id.good_image, R.id.te_evaluate_two);
        setData();
        doCommentHttp(count);
        recycle_user_comment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doCommentHttp(count);

            }
            @Override
            public void onLoadMore() {
                count = count+5;
                doCommentHttp(count);
            }
        });
    }
    private void ChenJinShi() {
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((CommentActivity) context)
                .apply();
    }
    private void setData() {
        image_movie_comment_activity.setImageURI(resultBean.getCommentHeadPic());
        username.setText(resultBean.getCommentUserName());
        username_evaluate.setText(resultBean.getCommentContent());
        long commentTime = resultBean.getCommentTime();
        String format = DateUtils.format(commentTime, "yyyy-MM-dd");
        time_movie.setText(format);
        good_text.setText(resultBean.getGreatNum() + "");
        news_text.setText(resultBean.getReplyNum() + "");
    }

    private void doCommentHttp(int count) {

        commentId = SharedPreferencesUtils.getInt(context, "commentId");
        Map<String, String> map = new HashMap<>();
        map.put("commentId", commentId + "");
        map.put("page", 1 + "");
        map.put("count", count+"");

        userId = SharedPreferencesUtils.getInt(context, "userId");
        sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Map mapHead = new HashMap<>();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("movieApi/movie/v1/findCommentReply", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                CommentItemBean commentItemBean = new Gson().fromJson(data, CommentItemBean.class);
                List<CommentItemBean.ResultBean> result = commentItemBean.getResult();
                if (result == null) {
                    Toast.makeText(context, "该用户暂无评论", Toast.LENGTH_SHORT).show();
                } else {
                    userNameCommentAdapter.setList(result);
                    recycle_user_comment.refreshComplete();
                    recycle_user_comment.loadMoreComplete();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui_01:
                ((CommentActivity) context).finish();
                break;
            case R.id.good_image:
                break;
            case R.id.te_evaluate_two:
                doUserNameCommentHttp();
                break;
        }
    }

    private void doUserNameCommentHttp() {
        String replyContent = ed_evaluate_two.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("commentId", commentId + "");
        map.put("replyContent", replyContent);
        Map mapHead = new HashMap<>();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "movieApi/movie/v1/verify/commentReply", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                doCommentHttp(count);
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail(String error) {

            }
        });
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
