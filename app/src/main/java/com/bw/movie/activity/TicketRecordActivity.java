package com.bw.movie.activity;

import android.content.Context;

import com.bw.movie.mvp.basepresenter.BaseActivityPresenter;
import com.bw.movie.presenter.TicketRecordActivityPresenter;

public class TicketRecordActivity extends BaseActivityPresenter<TicketRecordActivityPresenter> {
    @Override
    public Class<TicketRecordActivityPresenter> getClassDelegate() {
        return TicketRecordActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
