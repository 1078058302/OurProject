package com.bw.movie.presenter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.EmailActivity;
import com.bw.movie.activity.NiChengActivity;
import com.bw.movie.activity.ResetPwdActivity;
import com.bw.movie.activity.SexActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.model.UpdateBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.mvp.view.PhotoPopwindow;
import com.bw.movie.service.BaseService;
import com.bw.movie.utils.PermissionUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.bw.movie.utils.UltimateBar;
import com.bw.movie.wxapi.WXEntryActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static android.app.Activity.RESULT_OK;
/**
* @作者 THTF
* @创建日期 2018/12/06 14:12
*/
public class UserInfoActivityPresenter extends AppDelegate implements View.OnClickListener {
    private SimpleDraweeView mSd2;
    private TextView mUserNickName, mUserSex, mDataOfbirth, mPhoneNumber, mTvEmail, mTv_bind;
    private Bitmap heads;//头像bitmap
    private static String path;//sd路径
    private String mNickName;
    private int mSex;
    private String mEmaila;
    private int REQUEST_TAKE_PHOTO_PERMISSION = 1;
    private ImageView mIv_return;
    private IWXAPI api;
    private static final String APP_ID = "wxb3852e6a6b7d9516";
    private boolean isWXbind = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;

    }

    @Override
    public void initData() {
        super.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build((UserInfoActivity) context).apply();
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(APP_ID);
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        setClick(this, R.id.rl_01, R.id.rl_02, R.id.rl_03, R.id.tuichuLogin, R.id.rl_06, R.id.rl_07, R.id.rl_08);
        mSd2 = (SimpleDraweeView) get(R.id.sd2);
        mUserNickName = (TextView) get(R.id.userNickname);
        mUserSex = (TextView) get(R.id.userSex);
        mDataOfbirth = (TextView) get(R.id.dataOfbirth);
        mPhoneNumber = (TextView) get(R.id.phoneNumber);
        mTvEmail = (TextView) get(R.id.tvEmail);
       mTv_bind = (TextView) get(R.id.tv_bind);
        mIv_return = (ImageView) get(R.id.iv_return);
        //接口回调方法
        WXEntryActivity wxEntryActivity = new WXEntryActivity();
        wxEntryActivity.setWXEntryListener(new WXEntryActivity.WXEntryBindListener() {
            @Override
            public void onBinds(boolean flag) {
                if (flag) {
                    isWXbind = true;
                    toast("绑定成功");
                    mTv_bind.setText("已绑定");
                } else {
                    isWXbind = false;
                    toast("请求失败");
                    mTv_bind.setText("未绑定");
                }
            }
        });

    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_01:
                //拍照
                show();
                break;
            case R.id.rl_02:
                //昵称
                Intent intent = new Intent(context, NiChengActivity.class);
                context.startActivity(intent);
                break;
            case R.id.rl_03:
                //性别
                context.startActivity(new Intent(context, SexActivity.class));
                break;
            case R.id.tuichuLogin:
                //退出登录
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定要退出登录吗？").
                        setTitle("提示").
                        setCancelable(true).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tuichu();
                                toast("退出成功");
                                ((UserInfoActivity) context).finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.rl_06:
                //邮箱
                context.startActivity(new Intent(context, EmailActivity.class));
                break;
            case R.id.rl_07:
                //重置密码
                context.startActivity(new Intent(context, ResetPwdActivity.class));
                break;
            case R.id.rl_08:
                //微信绑定
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
//        req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信绑定";
                api.sendReq(req);

        }
    }


    public void onResume() {
        String mHeadPic = SharedPreferencesUtils.getString(context, "headPic");
        String mHeadPath = SharedPreferencesUtils.getString(context, "headPath");
        if (!TextUtils.isEmpty(mHeadPath)) {
            mSd2.setImageURI(mHeadPath);
        } else {
            if (!TextUtils.isEmpty(mHeadPic)) {
                mSd2.setImageURI(mHeadPic);

            } else {
                mSd2.setImageResource(R.mipmap.logo);
            }
        }
        mSex = SharedPreferencesUtils.getInt(context, "sex");
        if (mSex == 1) {
            mUserSex.setText("男");
        } else {
            mUserSex.setText("女");
        }
        mNickName = SharedPreferencesUtils.getString(context, "nickName");
        if (!TextUtils.isEmpty(mNickName)) {
            mUserNickName.setText(mNickName);
        } else {
            mUserNickName.setText(mNickName);
        }


        String birthday = SharedPreferencesUtils.getString(context, "birthday");
        if (!TextUtils.isEmpty(birthday)) {
            mDataOfbirth.setText(birthday);
        }
        String tv_phone2 = SharedPreferencesUtils.getString(context, "tv_phone2");
        if (!TextUtils.isEmpty(tv_phone2)) {
            mPhoneNumber.setText(tv_phone2);
        }

        mEmaila = SharedPreferencesUtils.getString(context, "email");
        if (!TextUtils.isEmpty(mEmaila)) {
            mTvEmail.setText(mEmaila);
        } else {
            mTvEmail.setText(mEmaila);
        }
        // 清除信息
        String tv_phone = SharedPreferencesUtils.getString(context, "tv_phone2");
        if (TextUtils.isEmpty(tv_phone)) {
            mPhoneNumber.setText(tv_phone);
        }
        int sex = SharedPreferencesUtils.getInt(context, "sex");
        if (sex == 0) {
            mUserSex.setText("");
        }
        String birthday1 = SharedPreferencesUtils.getString(context, "birthday");
        if (TextUtils.isEmpty(birthday1)) {
            mDataOfbirth.setText(birthday1);

        }
        String email1 = SharedPreferencesUtils.getString(context, "email");
        if (TextUtils.isEmpty(email1)) {
            mTvEmail.setText(email1);
        }
        String tv_phones = SharedPreferencesUtils.getString(context, "tv_phone");
        if (TextUtils.isEmpty(tv_phone)) {
            mTvEmail.setText(tv_phones);
        }
    }

    private void show() {
        new PhotoPopwindow(context, get(R.id.layout_parent), new PhotoPopwindow.OnSelectPictureListener() {
            @Override
            public void onTakePhoto() {
                //拍照
                //动态权限
                //6.0以上动态获取权限
                PermissionUtils.permission(context, new PermissionUtils.PermissionListener() {
                    @Override
                    public void success() {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "heads.png")));
                        ((UserInfoActivity) context).startActivityForResult(intent2, 2);
                    }
                });


            }

            @Override
            public void onSelectPicture() {
                //相册选择
                //动态权限
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((UserInfoActivity) context).startActivityForResult(intent1, 1);

            }

            @Override
            public void onCancel() {

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/heads.png");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    heads = extras.getParcelable("data");
                    if (heads != null) {
                        mSd2.setImageBitmap(heads);
                        String fileName = path + "/heads.png";//图片名字
                        setPicToView(heads);//保存在SD卡中
                        File file1 = new File(fileName);
                        uploadPic(file1);
                        //uploadImage(fileName);
                    }
                }
                break;
            default:
                break;
        }

    }

    //Retrofit上传
    private void uploadPic(File file1) {
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        Log.i("ss", userId + "");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        Log.i("sss", sessionId);
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        m.put("sessionId", sessionId);
        final RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", "heads.png", file);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://mobile.bwstudent.com/").build();
        retrofit.create(BaseService.class)
                .upload(m, part).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Gson gson = new Gson();
                        UpdateBean updateBean = gson.fromJson(responseBody.string(), UpdateBean.class);
                        if ("0000".equals(updateBean.getStatus())) {
                            toast("上传成功");
                            String headPath = updateBean.getHeadPath();
                            SharedPreferencesUtils.putString(context, "headPath", headPath);
                            //上传头像
                            mSd2.setImageURI(headPath);
                            onResume();
                        } else {
                            toast("上传失败");
                        }
                    }
                });
    }


    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 127);
        intent.putExtra("outputY", 127);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false);//不启用人脸识别
        intent.putExtra("return-data", true);
        ((UserInfoActivity) context).startActivityForResult(intent, 3);
    }

    //存储
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/heads.png";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    //退出登录
    private void tuichu() {
        SharedPreferencesUtils.putString(context, "tv_phone2", "");
        SharedPreferencesUtils.putString(context, "tv_phone", "");
        SharedPreferencesUtils.putString(context, "tv_pwd", "");
        SharedPreferencesUtils.putString(context, "headPic", "");
        SharedPreferencesUtils.putString(context, "headPath", "");
        SharedPreferencesUtils.putString(context, "nickName", "");
        SharedPreferencesUtils.putString(context, "sessionId", "");
        SharedPreferencesUtils.putInt(context, "userId", 0);
        SharedPreferencesUtils.putInt(context, "sex", 0);
        SharedPreferencesUtils.putString(context, "birthday", "");
        SharedPreferencesUtils.putString(context, "email", "");
        onResume();
    }


}

