package com.bw.movie.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseService {
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> map);

    @GET
    Observable<ResponseBody> getHead(@Url String url, @QueryMap Map<String, String> map, @HeaderMap Map<String, String> mapHead);
    @Headers({
            "ak:0110010010000",
            "Content-Type:application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@HeaderMap Map<String, String> m, @Url String url, @FieldMap Map<String, String> map);

    @Multipart
    @POST("/movieApi/user/v1/verify/uploadHeadPic")
    Observable<ResponseBody> upload(@HeaderMap Map<String, String> m, @Part MultipartBody.Part part);

}
