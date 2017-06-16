package com.affiliate.helpers;


import com.affiliate.retrofitModels.GetEntityById;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface RetrofitInterface {

    @POST("/apisim/feedengine/getentitybyid")
    Call<ResponseBody> getEntityById(@Body GetEntityById body);



}