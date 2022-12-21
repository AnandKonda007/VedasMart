package com.example.vedasmart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    private static Controller retroFitController;
    private static Retrofit retrofit = null;
    private Context context;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Controller getInstance() {
        if (retroFitController == null) {
            retroFitController = new Controller();
        }
        return retroFitController;
    }

    public void fillcontext(Context context) {
        this.context = context;
    }

    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET");
                    return true;
                }
            }
        }
        return false;
    }

    public void ApiCallBackForPostMethods(Context context, String EndUrl, String token, JsonObject jsonobj, String eventBusMsg) {

        // public void ApiCallBackForPostMethods(Context context, String EndUrl, JsonObject jsonobj, String eventBusMsg) {
        this.context = context;
        Api api = getClient().create(Api.class);
        Call<ResponseBody> call = api.postMethodApi(token, jsonobj, EndUrl);
        // Call<ResponseBody> call = api.postMethodApi(jsonobj, EndUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String Body = null;
                if (response.body() != null) {
                    try {
                        Body = new String(response.body().bytes());

                        EventBus.getDefault().post(new MessageEvent(Body, eventBusMsg));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    EventBus.getDefault().post(new MessageEvent(Body, eventBusMsg));
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                EventBus.getDefault().post(new MessageEvent(null, "onfailure"));
                Toast.makeText(context, ":" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class MessageEvent {
        public String body, msg;

        public MessageEvent(String body, String message) {
            this.body = body;
            this.msg = message;
        }
    }
}
