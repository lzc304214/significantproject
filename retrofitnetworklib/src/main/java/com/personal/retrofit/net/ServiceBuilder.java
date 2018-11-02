package com.personal.retrofit.net;


import android.util.Log;

import com.personal.retrofit.NetConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceBuilder {
    private static final String LOG_TAG = ServiceBuilder.class.getName();
    private Retrofit retrofit;

    private static ServiceBuilder builder;

    private ServiceBuilder() {
        // init okhttp 3 logger
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (message != null) {
                    Log.e(LOG_TAG, "OkHttp: " + message);
                }

            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .retryOnConnectionFailure(true)
                .connectTimeout(NetConstants.TIME_OUT, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(NetConstants.SERVER_URL)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }


    public synchronized static ServiceBuilder newInstance() {
        if (builder == null) {
            builder = new ServiceBuilder();
        }
        return builder;
    }


    public <T> T build(Class<T> clazz) {
        return retrofit.create(clazz);
    }


}
