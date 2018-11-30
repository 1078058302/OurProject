package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.NiChengActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.model.UpdateBean;
import com.bw.movie.mvp.view.AppDelegate;
import com.bw.movie.mvp.view.PhotoPopwindow;
import com.bw.movie.service.BaseService;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

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

public class UserInfoActivityPresenter extends AppDelegate implements View.OnClickListener {
    private RelativeLayout rl_01;
    private SimpleDraweeView sd2;
    private TextView userNickName, userSex, dataOfbirth, phoneNumber, tvEmail;
    private Bitmap heads;//头像bitmap
    private static String path;//sd路径
    private String nickName;
    private int sex;
    private String emaila;

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;

    }

    @Override
    public void initData() {
        super.initData();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        setClick(this, R.id.rl_01, R.id.rl_02);
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
                intent.putExtra("nickName", nickName);
                intent.putExtra("sex",sex+"");
                intent.putExtra("email", emaila);
                context.startActivity(intent);
                break;
        }
    }


    public void onResume() {
        String headPath = SharedPreferencesUtils.getString(context, "headPath");
        Log.i("sss",headPath);
//        String headPic = SharedPreferencesUtils.getString(context, "headPic");
        if (!TextUtils.isEmpty(headPath)) {
            sd2.setImageURI(headPath);
        }
        String nickName = SharedPreferencesUtils.getString(context, "nickName");
        if (!TextUtils.isEmpty(nickName)) {
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

    }

    private void show() {
        new PhotoPopwindow(context, get(R.id.layout_parent), new PhotoPopwindow.OnSelectPictureListener() {
            @Override
            public void onTakePhoto() {
                //拍照
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "heads.png")));
                ((UserInfoActivity) context).startActivityForResult(intent2, 2);
            }

            @Override
            public void onSelectPicture() {
                //相册选择
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
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
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
                            Log.i("sss",headPath);
                            SharedPreferencesUtils.putString(context, "headPath", headPath);
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
}
