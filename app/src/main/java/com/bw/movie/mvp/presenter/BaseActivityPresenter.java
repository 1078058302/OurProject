package com.bw.movie.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.mvp.view.AppDelegate;


public abstract class BaseActivityPresenter<T extends AppDelegate> extends AppCompatActivity {


    public T delegate;

    public abstract Class<T> getClassDelegate();

    public BaseActivityPresenter() {
        try {
            delegate = getClassDelegate().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContext(this);
        delegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(delegate.rootView());
        delegate.initData();
    }

    public void getContext(Context context) {

    }
}
