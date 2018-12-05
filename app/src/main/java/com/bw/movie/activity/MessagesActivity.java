package com.bw.movie.activity;

import android.content.Context;
import android.widget.Toast;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.MessagesActivityPresenter;

public class MessagesActivity extends BaseActivityPresenter<MessagesActivityPresenter> {
    @Override
    public Class<MessagesActivityPresenter> getClassDelegate() {
        return MessagesActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.onResume();

    }
}
