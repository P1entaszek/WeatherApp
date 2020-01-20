package com.prod.weatherapp.datasource.retrofit;


import com.prod.weatherapp.datasource.model.ApiData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public interface ApiService {

    @GET("forecast/")
    Observable<ApiData> getWeatherData(@Query("lat") String latitude,
                                     @Query("lon") String longitude,
                                     @Query("appid") String appID);
}
