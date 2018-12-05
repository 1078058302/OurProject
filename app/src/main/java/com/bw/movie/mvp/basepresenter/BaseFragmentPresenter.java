package com.bw.movie.mvp.basepresenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bw.movie.mvp.view.AppDelegate;


public abstract class BaseFragmentPresenter<T extends AppDelegate> extends Fragment {
    public T delegate;

    public abstract Class<T> getClassDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            delegate = getClassDelegate().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getContext(getActivity());
        delegate.create(inflater, container, savedInstanceState);
        return delegate.rootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


        delegate.initData();

    }



    public void getContext(Context context) {

    }

    public void initView() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        delegate.destroy();
    }
}

