package com.bw.movie.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.NiChengActivity;
import com.bw.movie.activity.SexActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.model.UpdateBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.mvp.view.PhotoPopwindow;
import com.bw.movie.service.BaseService;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
import static com.bw.movie.net.NetWorkInterceptor.TAG;

public class UserInfoActivityPresenter extends AppDelegate implements View.OnClickListener {
    private RelativeLayout rl_01;
    private SimpleDraweeView sd2;
    private TextView userNickName, userSex, dataOfbirth, phoneNumber, tvEmail;
    private Bitmap heads;//头像bitmap
    private static String path;//sd路径
    private String nickName;
    private int sex;
    private String emaila;
    private int REQUEST_TAKE_PHOTO_PERMISSION = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;

    }

    @Override
    public void initData() {
        super.initData();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        setClick(this, R.id.rl_01, R.id.rl_02, R.id.rl_03, R.id.tuichuLogin);
        sd2 = (SimpleDraweeView) get(R.id.sd2);
        userNickName = (TextView) get(R.id.userNickname);
        userSex = (TextView) get(R.id.userSex);
        dataOfbirth = (TextView) get(R.id.dataOfbirth);
        phoneNumber = (TextView) get(R.id.phoneNumber);
        tvEmail = (TextView) get(R.id.tvEmail);


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
                tuichu();
                ((UserInfoActivity) context).finish();
                break;

        }
    }


    public void onResume() {
        String headPic = SharedPreferencesUtils.getString(context, "headPic");
        String headPath = SharedPreferencesUtils.getString(context, "headPath");
        if (!TextUtils.isEmpty(headPath)) {
            sd2.setImageURI(headPath);

        } else {
            if (!TextUtils.isEmpty(headPic)) {
                sd2.setImageURI(headPic);

            } else {
                sd2.setImageResource(R.mipmap.logo);
            }
        }
        nickName = SharedPreferencesUtils.getString(context, "nickName");
        if (!TextUtils.isEmpty(nickName)) {
            userNickName.setText(nickName);
        } else {
            userNickName.setText(nickName);
        }

        sex = SharedPreferencesUtils.getInt(context, "sex");
        if (sex == 1) {
            userSex.setText("男");
        } else {
            userSex.setText("女");
        }
        String birthday = SharedPreferencesUtils.getString(context, "birthday");
        if (!TextUtils.isEmpty(birthday)) {
            dataOfbirth.setText(birthday);
        }
        String tv_phone2 = SharedPreferencesUtils.getString(context, "tv_phone2");
        if (!TextUtils.isEmpty(tv_phone2)) {
            phoneNumber.setText(tv_phone2);
        }

        emaila = SharedPreferencesUtils.getString(context, "email");
        if (!TextUtils.isEmpty(emaila)) {
            tvEmail.setText(emaila);
        }
        // 清除信息
        String tv_phone = SharedPreferencesUtils.getString(context, "tv_phone2");
        if (TextUtils.isEmpty(tv_phone)) {
            phoneNumber.setText(tv_phone);
        }
        int sex = SharedPreferencesUtils.getInt(context, "sex");
        if (sex == 0) {
            userSex.setText("");
        }
//        String headPic = SharedPreferencesUtils.getString(context, "headPic");
//        if (TextUtils.isEmpty(headPic)) {
//            sd2.setImageURI(String.valueOf(R.mipmap.logo));
//        }
//        String headPath1 = SharedPreferencesUtils.getString(context, "headPath");
//        if (TextUtils.isEmpty(headPath1)) {
//            sd2.setImageURI(String.valueOf(R.mipmap.logo));
//        }
        String birthday1 = SharedPreferencesUtils.getString(context, "birthday");
        if (TextUtils.isEmpty(birthday1)) {
            dataOfbirth.setText(birthday1);

        }
        String email1 = SharedPreferencesUtils.getString(context, "email");
        if (TextUtils.isEmpty(email1)) {
            tvEmail.setText(email1);
        }
        String tv_phones = SharedPreferencesUtils.getString(context, "tv_phone");
        if (TextUtils.isEmpty(tv_phone)) {
            tvEmail.setText(tv_phones);
        }


    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //申请成功，可以拍照
//                show();
//            } else {
//                Toast.makeText(((UserInfoActivity) context), "你拒绝了权限，该功能不可用\n可在应用设置里授权拍照哦", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }

   // }

    private void show() {
        new PhotoPopwindow(context, get(R.id.layout_parent), new PhotoPopwindow.OnSelectPictureListener() {
            @Override
            public void onTakePhoto() {
                //拍照
                //动态权限
                //6.0以上动态获取权限
                if (ContextCompat.checkSelfPermission(((UserInfoActivity) context), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                    ActivityCompat.requestPermissions(((UserInfoActivity) context),
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_TAKE_PHOTO_PERMISSION);
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "heads.png")));
                    ((UserInfoActivity) context).startActivityForResult(intent2, 2);

                }


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
                        sd2.setImageBitmap(heads);
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
//                        Log.i("ResponseBody", responseBody.string());
//                        toast("上传成功");
                        Gson gson = new Gson();
                        UpdateBean updateBean = gson.fromJson(responseBody.string(), UpdateBean.class);
                        if ("0000".equals(updateBean.getStatus())) {
                            toast("上传成功");
                            String headPath = updateBean.getHeadPath();
                            SharedPreferencesUtils.putString(context, "headPath", headPath);
                            //上传头像
                            sd2.setImageURI(headPath);
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

//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //申请成功，可以拍照
//                show();
//            } else {
//                Toast.makeText(((UserInfoActivity) context), "你拒绝了权限，该功能不可用\n可在应用设置里授权拍照哦", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//    }

}

