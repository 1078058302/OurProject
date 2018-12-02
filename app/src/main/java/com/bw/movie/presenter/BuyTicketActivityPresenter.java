package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.BuyTicketActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.mvp.view.SeatTable;
import com.bw.movie.utils.UltimateBar;

public class BuyTicketActivityPresenter extends AppDelegate {

    private SeatTable seatTable;
    private int seatsTotal;
    private int seatsUseCount;
    private int num = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_ticket;
    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((BuyTicketActivity) context).apply();
        TextView begin_tiem = get(R.id.begin_time);
        TextView end_time = get(R.id.end_time);
        TextView cinema = get(R.id.cinema);
        TextView time = get(R.id.time);

        Intent intent = ((BuyTicketActivity) context).getIntent();
        String beginTime = intent.getStringExtra("beginTime");
        String endTime = intent.getStringExtra("endTime");
        seatsTotal = intent.getIntExtra("seatsTotal", 0);
        seatsUseCount = intent.getIntExtra("seatsUseCount", 0);
        String screeningHall = intent.getStringExtra("screeningHall");
        String duration = intent.getStringExtra("duration");
        int price = intent.getIntExtra("price", 0);
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
//                Toast.makeText(context, row + ".................." + column, Toast.LENGTH_SHORT).show();
                num++;
//                Toast.makeText(context, num+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unCheck(int row, int column) {
//                Toast.makeText(context, row + ".................." + column, Toast.LENGTH_SHORT).show();
                num--;
//                Toast.makeText(context, num+"", Toast.LENGTH_SHORT).show();
            }


            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
