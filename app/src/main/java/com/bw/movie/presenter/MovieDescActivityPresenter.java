package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.FilmCinemaActivity;
import com.bw.movie.activity.MovieDescActivity;
import com.bw.movie.adapter.MovieDetailsAdapter;
import com.bw.movie.adapter.MovieDonoticeAdapter;
import com.bw.movie.adapter.MovieEvaluateAdapter;
import com.bw.movie.adapter.MovieStillsAdapter;
import com.bw.movie.mvp.model.CollectonBena;
import com.bw.movie.mvp.model.EvaluateBean;
import com.bw.movie.mvp.model.MovieDescBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDescActivityPresenter extends AppDelegate implements View.OnClickListener {

    private int movie_id;
    private TextView moviename;
    private SimpleDraweeView movienameimage;
    private RelativeLayout movie_desc_relative_details;
    private RelativeLayout relative;
    private RelativeLayout movie_desc_relative_notice;
    private RelativeLayout movie_desc_relative_stills;
    private RelativeLayout movie_desc_relative_evaluate;
    private TextView details_type;
    private TextView details_director;
    private TextView details_time;
    private TextView details_address;
    private ImageView details_image_desc;
    private ImageView details_down_image;
    private TextView details_introduce;
    private XRecyclerView details_xrecycle;
    private RecyclerView stills_recycle;
    private ImageView stills_down_image;
    private MovieStillsAdapter movieStillsAdapter;
    private XRecyclerView evaluate_recycle;
    private ImageView evaluate_down_image;
    private ImageView notice_down_image;
    private MovieEvaluateAdapter movieEvaluateAdapter;
    private TextView buy_film;
    private MovieDescBean.ResultBean result = null;
    private RecyclerView notice_recycle;
    private ImageView image_collection;
    private ImageView evaluate_movie_image;
    private List<EvaluateBean.ResultBean> resultevaluate;
    private MovieDescBean movieDescBean;
    private EditText ed_evaluate;
    private TextView te_evaluate;
    private int count = 5;
    private SimpleDraweeView simpleDraweeView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_desc;
    }

    @Override
    public void initData() {
        super.initData();
        buy_film = (TextView) get(R.id.buy_film);
        moviename = get(R.id.movie_desc_name);
        image_collection = get(R.id.movie_desc_image_collection);
        movienameimage = get(R.id.movie_image_name);
        ed_evaluate = get(R.id.ed_evaluate);
        te_evaluate = get(R.id.te_evaluate);
        simpleDraweeView = get(R.id.title_image);
        //详情
        Button details = get(R.id.movie_desc_bt_details);
        //预告
        Button Notice = get(R.id.movie_desc_bt_Notice);
        //剧照
        Button Stills = get(R.id.movie_desc_bt_Stills);
        //影评
        Button evaluate = get(R.id.movie_desc_bt_evaluate);


        //详情 id
        details_type = get(R.id.details_type);
        details_director = get(R.id.details_director);
        details_time = get(R.id.details_time);
        details_address = get(R.id.details_address);
        details_image_desc = get(R.id.details_image_desc);
        details_down_image = get(R.id.details_down_image);
        details_introduce = get(R.id.details_introduce);
        details_xrecycle = get(R.id.details_xrecycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        details_xrecycle.setLayoutManager(linearLayoutManager);

        //剧照id
        stills_recycle = get(R.id.stills_recycle);
        stills_down_image = get(R.id.stills_down_image);

        //预告id
        notice_down_image = get(R.id.notice_down_image);
        notice_recycle = get(R.id.Notice_recycle);
        evaluate_movie_image = get(R.id.evaluate_movie_image);


        //影评id
        evaluate_recycle = get(R.id.evaluate_recycle);
        evaluate_down_image = get(R.id.evaluate_down_image);


        //详情
        movie_desc_relative_details = get(R.id.movie_desc_relative_details);
        //预告
        movie_desc_relative_notice = get(R.id.movie_desc_relative_Notice);
        //剧照
        movie_desc_relative_stills = get(R.id.movie_desc_relative_Stills);
        //影评
        movie_desc_relative_evaluate = get(R.id.movie_desc_relative_evaluate);
        //点击事件
        setClick(this, R.id.movie_desc_bt_details,
                R.id.movie_desc_bt_Notice,
                R.id.movie_desc_bt_Stills,
                R.id.movie_desc_bt_evaluate,
                R.id.details_down_image,
                R.id.notice_down_image,
                R.id.stills_down_image,
                R.id.evaluate_down_image,
                R.id.movie_desc_image_collection,
                R.id.te_evaluate);
        buttonBeyondKeyboardLayout(movie_desc_relative_evaluate, te_evaluate);
        evaluate_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doevaluate(count);
            }

            @Override
            public void onLoadMore() {
                count = count + 5;
                doevaluate(count);
            }
        });
        ChenJinShi();
        Intent intent = ((MovieDescActivity) context).getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);

        Log.i("MovieDescActivity", "" + movie_id);
        SharedPreferencesUtils.putInt(context, "movie_id", movie_id);
        if (!TextUtils.isEmpty(movie_id + "")) {
            SharedPreferencesUtils.putInt(context, "movice_ids", movie_id);
        }
        //展示电影详情
        doMovieDescHttp();
        //展示影评
        doevaluate(count);

        movieEvaluateAdapter = new MovieEvaluateAdapter(context);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        evaluate_recycle.setLayoutManager(linearLayoutManager1);
        evaluate_recycle.setAdapter(movieEvaluateAdapter);
        buy_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmCinemaActivity.class);
                if (result != null) {
                    if (result.getName() != null) {
                        intent.putExtra("name", result.getName());
                    } else {
                        intent.putExtra("name", "请购买");

                    }
                    ((MovieDescActivity) context).startActivity(intent);

                }

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

    private void ChenJinShi() {
        UltimateBar.newImmersionBuilder()
                .applyNav(true)
                .build((MovieDescActivity) context)
                .apply();
    }

    private void doMovieDescHttp() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movie_id + "");
        Map<String, String> mapHead = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("movieApi/movie/v1/findMoviesDetail", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                movieDescBean = new Gson().fromJson(data, MovieDescBean.class);
                result = movieDescBean.getResult();
                moviename.setText(result.getName());

                if (movieDescBean.getResult().isFollowMovie()) {
                    image_collection.setImageResource(R.mipmap.collection_default);
                } else {
                    image_collection.setImageResource(R.mipmap.collection_selected);
                }

                movienameimage.setImageURI(result.getImageUrl());
                showUrlBlur(simpleDraweeView, result.getImageUrl(), 2, 2);
                //详情
                dodetails(result);
                //剧照
                dostills(result);
                //预告
                donotice(result);
            }
//
            @Override
            public void fail(String error) {

            }
        });
    }

    //高斯模糊
    public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(6, blurRadius))
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //预告片
    private void donotice(MovieDescBean.ResultBean result) {
        List<MovieDescBean.ResultBean.ShortFilmListBean> shortFilmList = result.getShortFilmList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notice_recycle.setLayoutManager(linearLayoutManager);
        MovieDonoticeAdapter movieDonoticeAdapter = new MovieDonoticeAdapter(context);
        movieDonoticeAdapter.setList(shortFilmList);
        notice_recycle.setAdapter(movieDonoticeAdapter);
    }

    //影评layout赋值
    private void doevaluate(int count) {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movie_id + "");
        map.put("page", 1 + "");
        map.put("count", count + "");
        Map<String, String> mapHead = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);

        new HttpHelper().getHead("movieApi/movie/v1/findAllMovieComment", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                EvaluateBean evaluateBean = new Gson().fromJson(data, EvaluateBean.class);
                resultevaluate = evaluateBean.getResult();
                movieEvaluateAdapter.setList(resultevaluate);
                evaluate_recycle.refreshComplete();
                evaluate_recycle.loadMoreComplete();
            }

            @Override
            public void fail(String error) {

            }
        });


    }

    //剧照layout赋值
    private void dostills(MovieDescBean.ResultBean result) {
        List<String> posterList = result.getPosterList();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager( context, 2 );
        stills_recycle.setLayoutManager(staggeredGridLayoutManager);
        movieStillsAdapter = new MovieStillsAdapter(context);
        stills_recycle.setAdapter(movieStillsAdapter);
        movieStillsAdapter.setList(posterList);
    }

    //详情layout赋值
    private void dodetails(MovieDescBean.ResultBean result) {
        Glide.with(context).load(result.getImageUrl()).into(details_image_desc);
        details_type.setText("类型: " + result.getMovieTypes());
        details_director.setText("导演: " + result.getDirector());
        details_time.setText("时长: " + result.getDuration());
        details_address.setText("产地: " + result.getPlaceOrigin());
        details_introduce.setText(result.getSummary());
        String starring = result.getStarring();
        String[] split = starring.split(",");
        ArrayList<String> nameList = new ArrayList<String>();
        for (int i = 0; i < split.length; i++) {
            nameList.add(split[i]);
        }
        MovieDetailsAdapter movieDetailsAdapter = new MovieDetailsAdapter(context);
        movieDetailsAdapter.setList(nameList);
        details_xrecycle.setAdapter(movieDetailsAdapter);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //+++++++++++++++++++++++++++++++++++++++++++++++++显示++++++++++++++++++++++++++++++++++++++++++++++++++++
            //详情showLayout
            case R.id.movie_desc_bt_details:
                relative = movie_desc_relative_details;
                showShopCar(relative);
                break;
            //预告showLayout
            case R.id.movie_desc_bt_Notice:
                relative = movie_desc_relative_notice;
                showShopCar(relative);
                break;
            //剧照showLayout
            case R.id.movie_desc_bt_Stills:
                relative = movie_desc_relative_stills;
                showShopCar(relative);
                break;
            //影评showLayout
            case R.id.movie_desc_bt_evaluate:
                relative = movie_desc_relative_evaluate;
                showShopCar(relative);
                break;
            //+++++++++++++++++++++++++++++++++++++++++++++++++隐藏++++++++++++++++++++++++++++++++++++++++++++++++++++
            //详情hintlayout
            case R.id.details_down_image:
                relative = movie_desc_relative_details;
                hintShopCar(relative);
            case R.id.notice_down_image:
                relative = movie_desc_relative_notice;
                hintShopCar(relative);
                break;
            //剧照hintlayout
            case R.id.stills_down_image:
                relative = movie_desc_relative_stills;
                hintShopCar(relative);
                break;
            //影评hintlayout
            case R.id.evaluate_down_image:
                relative = movie_desc_relative_evaluate;
                hintShopCar(relative);
                break;
            //收藏
            case R.id.movie_desc_image_collection:
                initCollection();
                break;
            //发送评论
            case R.id.te_evaluate:
                doTeEvaluate();
                break;
        }
    }

    //发送评论
    private void doTeEvaluate() {
        String commentContent = ed_evaluate.getText().toString().trim();
        Map map = new HashMap<>();
        Toast.makeText(context, "movieId" + movie_id, Toast.LENGTH_SHORT).show();
        map.put("movieId", movie_id);
        map.put("commentContent", commentContent);
        Map mapHead = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "movieApi/movie/v1/verify/movieComment", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                doevaluate(count);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //点击收藏
    private void initCollection() {
        if (movieDescBean.getResult().isFollowMovie()) {
            image_collection.setImageResource(R.mipmap.collection_selected);
            doHttpSelected();
        } else {
            image_collection.setImageResource(R.mipmap.collection_default);
            doHttpDeafult();
        }
    }

    //取消收藏
    private void doHttpDeafult() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movie_id + "");
        Map<String, String> mapHead = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("movieApi/movie/v1/verify/cancelFollowMovie", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                CollectonBena collectonBena = new Gson().fromJson(data, CollectonBena.class);
                Toast.makeText(context, "" + collectonBena.getMessage(), Toast.LENGTH_SHORT).show();
                doMovieDescHttp();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //收藏
    private void doHttpSelected() {
        Map<String, String> map = new HashMap<>();
        map.put("movieId", movie_id + "");
        Map<String, String> mapHead = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        mapHead.put("userId", userId + "");
        mapHead.put("sessionId", sessionId);
        new HttpHelper().getHead("movieApi/movie/v1/verify/followMovie", map, mapHead).result(new HttpListener() {
            @Override
            public void success(String data) {
                CollectonBena collectonBena = new Gson().fromJson(data, CollectonBena.class);
                Toast.makeText(context, "" + collectonBena.getMessage(), Toast.LENGTH_SHORT).show();
                doMovieDescHttp();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void showShopCar(RelativeLayout relative) {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relative, "translationY", heightPixels, 0);
        objectAnimator.setDuration(400);
        objectAnimator.start();
        relative.setVisibility(View.VISIBLE);
    }

    //关闭
    private void hintShopCar(final RelativeLayout relative) {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relative, "translationY", 0, heightPixels);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relative.setVisibility(View.GONE);
            }
        }, 400);

    }
}
