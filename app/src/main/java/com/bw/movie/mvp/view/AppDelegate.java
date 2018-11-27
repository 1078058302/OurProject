package com.bw.movie.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class AppDelegate implements Delegate {
    private int layoutId;
    private View rootView;
    private SparseArray<View> views = new SparseArray<>();
    public Context context;

    public <T extends View> T get(int id) {
        T view = (T) views.get(id);
        if (view == null) {
            view = rootView.findViewById(id);
            views.put(id, view);
        }
        return view;
    }

    public void setClick(View.OnClickListener listener, int... ids) {

        if (ids == null) {

            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public void toast(String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initData() {

    }

    @Override
    public void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = inflater.inflate(getLayoutId(), viewGroup, false);
    }

    @Override
    public View rootView() {
        return rootView;
    }


    public abstract int getLayoutId();

    public void destroy() {
        rootView = null;
    }
}
