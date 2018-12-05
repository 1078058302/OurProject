package com.bw.movie.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.BuyTicketActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.SuccessShowActivity;
import com.bw.movie.mvp.model.OrderBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.mvp.view.SeatTable;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class BuyTicketActivityPresenter extends AppDelegate {

    private SeatTable seatTable;
    private int seatsTotal;
    private int seatsUseCount;
    private int num = 0;
    private ImageView sure;
    private ImageView no;
    private int status;
    private TextView price_all;
    private int price;
    private RelativeLayout show_pay;
    private RelativeLayout alpha_layout;
    private TextView pay_price;
    private ImageView close;
    private RelativeLayout weixin_layout;
    private RelativeLayout zhifubao_layout;
    private String sessionId;
    private int userId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_ticket;
    }

    @Override
    public void initData() {
        super.initData();
        sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        userId = SharedPreferencesUtils.getInt(context, "userId");
        UltimateBar.newImmersionBuilder().applyNav(false).build((BuyTicketActivity) context).apply();
        TextView begin_tiem = get(R.id.begin_time);
        TextView end_time = get(R.id.end_time);
        TextView cinema = get(R.id.cinema);
        TextView time = get(R.id.time);
        price_all = get(R.id.price);
        sure = get(R.id.sure);
        no = get(R.id.no);
        pay_price = get(R.id.pay_price);
        alpha_layout = get(R.id.alpha_layout);
        show_pay = get(R.id.show_pay);
        close = get(R.id.close);
        Intent intent = ((BuyTicketActivity) context).getIntent();
        String beginTime = intent.getStringExtra("beginTime");
        String endTime = intent.getStringExtra("endTime");
        seatsTotal = intent.getIntExtra("seatsTotal", 0);
        status = intent.getIntExtra("status", 0);
        seatsUseCount = intent.getIntExtra("seatsUseCount", 0);
        String screeningHall = intent.getStringExtra("screeningHall");
        String duration = intent.getStringExtra("duration");
        price = intent.getIntExtra("price", 0);
        weixin_layout = get(R.id.weixin_layout);
        zhifubao_layout = get(R.id.zhifubao_layout);
        begin_tiem.setText(beginTime);
        end_time.setText(endTime);
        cinema.setText(screeningHall);
        time.setText(duration);
        seatTable = get(R.id.seat_table);
        int i = seatsTotal / 8;
        seatTable.setData(7, i);

        seatTable.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                int num = 0;
                int i1 = seatsUseCount / 8;
                for (int j = 0; j < i1; j++) {
                    for (int k = 5; k <= 14; k++) {
                        if (row == j && column == k) {
                            num++;
                            if (num == seatsUseCount) {
                                break;
                            }
                            return true;
                        }
                    }

                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                num++;
                int price_a = price * num;
                price_all.setText(price_a + "");
            }

            @Override
            public void unCheck(int row, int column) {
                num--;
                int price_a = price * num;
                price_all.setText(price_a + "");
            }


            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, num + "", Toast.LENGTH_SHORT).show();
                if (num != 0) {
                    doHttp();
                } else {
                    Toast.makeText(context, "请先选择座位", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alpha_layout.setVisibility(View.GONE);
                hintShopCar();
            }
        });
    }

    private void doHttp() {

        String userIds = userId + "";
        String num1 = num + "";
        String status1 = status + "";
        String all = userIds + status1 + num1 + "movie";
        String sign = stringToMD5(all);
        Map map = new HashMap();
        Map mapHead = new HashMap();
        map.put("scheduleId", status);
        map.put("amount", num);
        map.put("sign", sign);
//        Toast.makeText(context, all + "....." + status + "......" + num + "....." + userId, Toast.LENGTH_SHORT).show();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "/movieApi/movie/v1/verify/buyMovieTicket", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                final OrderBean orderBean = new Gson().fromJson(data, OrderBean.class);
                String message = orderBean.getMessage();
                if (message.equals("下单成功")) {
                    int price_a = price * num;
                    alpha_layout.setVisibility(View.VISIBLE);
                    alpha_layout.setBackgroundResource(R.drawable.black_bg);
                    pay_price.setText(price_a + ".00");
                    showShopCar();
                    weixin_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context, "微信支付", Toast.LENGTH_SHORT).show();
                            String orderId = orderBean.getOrderId();
                            doHttpPay(orderId);
                        }
                    });
                    zhifubao_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "暂不支持支付宝支付", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doHttpPay(String orderId) {
        ///movieApi/movie/v1/verify/pay
        Map map = new HashMap();
        map.put("payType", 1);
        map.put("orderId", orderId);
        Map mapHead = new HashMap();
        mapHead.put("userId", userId);
        mapHead.put("sessionId", sessionId);
        new HttpHelper().post(mapHead, "/movieApi/movie/v1/verify/pay", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("支付成功")) {
                    alpha_layout.setVisibility(View.GONE);
                    hintShopCar();
                    Intent intent = new Intent(context, SuccessShowActivity.class);
                    int i = price * num;
                    intent.putExtra("price", i);
                    context.startActivity(intent);
                    ((BuyTicketActivity) context).finish();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    private void showShopCar() {
        int height = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(show_pay, "translationY", height, 0);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        show_pay.setVisibility(View.VISIBLE);

    }

    //关闭
    private void hintShopCar() {
        int height = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(show_pay, "translationY", 0, height);
        objectAnimator.setDuration(0);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                show_pay.setVisibility(View.GONE);
            }
        }, 0);

    }
}
