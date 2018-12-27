package com.bw.movie.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;


//获取动态权限
public class PermissionUtils {

    public static void permission(final Context context, final PermissionListener listener) {
        AndPermission.with(context)
                .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.ACCESS_COARSE_LOCATION,
                        Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.success();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (!permissions.isEmpty()) {
                            Uri packageURI = Uri.parse("package:" + context.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            Toast.makeText(context, "请找到权限管理，打开相机权限", Toast.LENGTH_LONG).show();

                        } else {
                            listener.success();
                        }
                    }
                }).start();
    }

    public interface PermissionListener {
        void success();
    }
}

