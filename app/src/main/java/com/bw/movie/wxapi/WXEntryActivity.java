package com.bw.movie.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.App;
import com.bw.movie.mvp.model.LoginBean;
import com.bw.movie.net.HttpHelper;
import com.bw.movie.net.HttpListener;
import com.bw.movie.utils.DateUtils;
import com.bw.movie.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;


/**
 * by:majunbao
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        App.mWxApi.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        Log.d("WXEntryActivity", "错误码 : " + resp.errCode + "");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {

                    Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                //微信登录
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                    final String code = sendResp.code;
                    String state = sendResp.state;
                    if (state.equals("wechat_sdk_微信登录")) {
                        doWxLogin(code);
                    } else if (state.equals("wechat_sdk_微信绑定")) {
                        doWxBind(code);
                    }
                }
                switch (resp.getType()) {

                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        Toast.makeText(this, "微信分享成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                break;
        }
        //支付成功
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果");
            builder.setMessage(resp.errCode + "");
            builder.show();
//            Intent intent = new Intent(this, SuccessShowActivity.class);
//            startActivity(intent);
        }
    }

    //微信登录
    private void doWxLogin(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        new HttpHelper().post(null, "/movieApi/user/v1/weChatBindingLogin", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                if ("0000".equals(loginBean.getStatus())) {
                    Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.putInt(WXEntryActivity.this, "userId", loginBean.getResult().getUserId());
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "sessionId", loginBean.getResult().getSessionId());
                    LoginBean.ResultBean.UserInfoBean userInfo = loginBean.getResult().getUserInfo();
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "nickName", userInfo.getNickName());
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "phone", userInfo.getPhone());
                    long birthday = userInfo.getBirthday();
                    String format = DateUtils.format(birthday, "yyyy-MM-dd");
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "birthday", format);
                    SharedPreferencesUtils.putInt(WXEntryActivity.this, "sex", userInfo.getSex());
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "headPic", userInfo.getHeadPic());
                }

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //微信绑定
    private void doWxBind(String code) {
        int userId = SharedPreferencesUtils.getInt(WXEntryActivity.this, "userId");
        Map<String, String> m = new HashMap<>();
        m.put("userId", userId + "");
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        new HttpHelper().post(m, "/movieApi/user/v1/verify/bindWeChat", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Toast.makeText(WXEntryActivity.this, data, Toast.LENGTH_SHORT).show();
                if (data.contains("绑定成功")) {
                    listener.onBinds(true);
                }
            }

            @Override
            public void fail(String error) {
                Toast.makeText(WXEntryActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                listener.onBinds(false);
            }
        });
    }

    //接口回调
    private WXEntryBindListener listener;
    public void setWXEntryListener(WXEntryBindListener listener) {
        this.listener = listener;
    }
    public interface WXEntryBindListener {
        void onBinds(boolean flag);

    }
}

