package com.prod.weatherapp.datasource.features.weather.MVP;

import androidx.annotation.Nullable;

import com.prod.weatherapp.datasource.ServerResponseListener;
import com.prod.weatherapp.datasource.WeatherAppClient;
import com.prod.weatherapp.datasource.model.ApiData;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class Interactor implements MVPContract.Interactor {
    @Override
    public void getWeatherData(String lat, String lon, WeatherDataInteractorCallback callback) {
        WeatherAppClient.getWeatherAppClient().getAllWeatherData(lat, lon, new ServerResponseListener<ApiData>() {
            @Override
            public void onSuccess(@Nullable ApiData apiData) {
                callback.onGetWeatherDataSuccesCallback(apiData);
            }

            @Override
            public void onError(@Nullable Throwable error) {
                callback.onGetWeatherDataErrorCallback(error);
            }
        });
    }
}
