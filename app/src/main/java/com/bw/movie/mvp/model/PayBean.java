package com.bw.movie.mvp.model;

public class PayBean {

    /**
     * appId : wxb3852e6a6b7d9516
     * message : 支付成功
     * nonceStr : WQ72A7mNfUaA05Gw
     * partnerId : 1510865081
     * prepayId : wx07084938610450dcaad697243945045542
     * sign : 94F2FD993AB97C002E6CE898B04B578A
     * status : 0000
     * timeStamp : 1533602976
     * packageValue : Sign=WXPay
     */

    private String appId;
    private String message;
    private String nonceStr;
    private String partnerId;
    private String prepayId;
    private String sign;
    private String status;
    private String timeStamp;
    private String packageValue;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }
}
