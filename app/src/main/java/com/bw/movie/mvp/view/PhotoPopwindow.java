package com.bw.movie.mvp.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.bw.movie.R;



public class PhotoPopwindow implements View.OnClickListener {

    private Context context;
    private PopupWindow popupWindow = null;
    private OnSelectPictureListener listener = null;

    public PhotoPopwindow(Context mContext, View parentView, OnSelectPictureListener listener) {
        this.context = mContext;
        this.listener = listener;
        View view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_takephoto, null);
        initView(view, parentView);
    }

    //初始化方法
    private void initView(View view, View parentView) {
        popupWindow = new PopupWindow(parentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);//外部可点击
        popupWindow.setFocusable(true);
        //设置弹出窗体需要软键盘，
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //布局显示设置到底部
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        //设置显示动画
       // popupWindow.setAnimationStyle(R.style.take_photo_anim);
        //布局控件
        view.findViewById(R.id.btnTakePhoto).setOnClickListener(this);
        view.findViewById(R.id.btnChooseFromAlbum).setOnClickListener(this);
        view.findViewById(R.id.btnCancle).setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btnTakePhoto) {
            listener.onTakePhoto();//拍照
        } else if (i == R.id.btnChooseFromAlbum) {
            listener.onSelectPicture();//相册选择
        } else if (i == R.id.btnCancle) {
            listener.onCancel();//取消
        }
        popupWindow.dismiss();
    }

    //接口回调
    public interface OnSelectPictureListener {

        //拍照
        void onTakePhoto();

        //从相册选择
        void onSelectPicture();

        //取消
        void onCancel();
    }

}
