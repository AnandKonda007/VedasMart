package com.example.vedasmart;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
public interface Api {
    String BASE_URL = "http://liveapi-vmart.softexer.com/api/";
    String img_url = "http://liveapi-vmart.softexer.com/";
    String tokenString = "vmart";


    /*   @POST
       Call<ResponseBody> postMethodApi(@Body JsonObject jsonObject, @Url String url);*/
   @POST
    Call<ResponseBody> postMethodApi(@Header(tokenString) String token, @Body JsonObject jsonobject, @Url String url);
}
