package com.bw.movie.presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.model.DetailsBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DetailsShowFragmentPresenter extends AppDelegate {


    private Bundle arguments;
    private TextView address_details;
    private TextView phone_details;
    private TextView line_details;
    private TextView show_lines;

    @Override
    public int getLayoutId() {
        return R.layout.details_show;
    }

    @Override
    public void initData() {
        super.initData();
        int cinemaId = SharedPreferencesUtils.getInt(context, "cinemaId");
        address_details = get(R.id.address_details);
        phone_details = get(R.id.phone_details);
        line_details = get(R.id.line_details);
        show_lines = get(R.id.show_lines);
        doHttp(cinemaId);
    }

    private void doHttp(int cinemaId) {
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemaId + "");
        new HttpHelper().get("/movieApi/cinema/v1/findCinemaInfo", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                DetailsBean detailsBean = new Gson().fromJson(data, DetailsBean.class);
                DetailsBean.ResultBean result = detailsBean.getResult();
                String address = result.getAddress();
                String phone = result.getPhone();
                String vehicleRoute = result.getVehicleRoute();
                address_details.setText(address);
                phone_details.setText(phone);
                line_details.setText("乘车路线");
                show_lines.setText(vehicleRoute + "\n");
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;


    }

    public void setArguments(Bundle arguments) {
        this.arguments = arguments;
    }
}
