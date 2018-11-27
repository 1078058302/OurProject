package com.bw.movie.net;


import com.bw.movie.service.BaseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpHelper {
    private BaseService baseService;

    public HttpHelper() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetWorkInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://mobile.bwstudent.com/")
                .client(okHttpClient)
                .build();
        baseService = retrofit.create(BaseService.class);
    }

    public HttpHelper get(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    public HttpHelper post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    private Observer observer = new Observer<ResponseBody>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                listener.success(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String error = e.getMessage();
            listener.fail(error);
        }

        @Override
        public void onComplete() {

        }
    };

    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

}