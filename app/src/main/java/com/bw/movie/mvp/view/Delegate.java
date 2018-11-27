package com.bw.movie.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface Delegate {
    void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle);

    void initData();

    View rootView();
}
