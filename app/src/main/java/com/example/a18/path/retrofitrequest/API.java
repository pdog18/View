package com.example.a18.path.retrofitrequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by machao on 2017/11/23.
 */

public interface API {

    @GET("path")
    Call<String> get();

    @POST("path")
    Call<String> post();

    @GET("path")
    Call<String> query(@Query("query")String query);

    @GET("path")
    Call<String> queryMap(@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST("path")
    Call<String> formUrlEncoded_filed(@Field("filed")String filed);

    @POST("path/{path}")
    Call<String> path(@Path("path") String path);

    @Multipart
    @POST("path")
    Call<String> part(@Part("part") String part);

    @GET("path")
    Call<String> head(@Header("header_key")String header_value);

    @POST("path")
    Call<String> body(@Body User body);


    @POST("path")
    Call<String> postWithQuery(@Query("key")String value);



}
