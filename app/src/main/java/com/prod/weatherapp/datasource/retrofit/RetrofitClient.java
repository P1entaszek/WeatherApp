package com.prod.weatherapp.datasource.retrofit;

import com.prod.weatherapp.Config;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getRetrofitInstance()
    {
        if(instance==null){
            instance = new Retrofit.Builder().baseUrl(Config.openWeatherMapApiEndpoint)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}
