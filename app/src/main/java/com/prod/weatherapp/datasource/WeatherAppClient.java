package com.prod.weatherapp.datasource;

import com.prod.weatherapp.Config;
import com.prod.weatherapp.datasource.model.ApiData;
import com.prod.weatherapp.datasource.model.WeatherData;
import com.prod.weatherapp.datasource.retrofit.ApiService;
import com.prod.weatherapp.datasource.retrofit.RetrofitClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class WeatherAppClient {
    private static WeatherAppClient weatherAppClient;

    public static WeatherAppClient getWeatherAppClient(){
        if(weatherAppClient == null){
            weatherAppClient = new WeatherAppClient();
        }
        return weatherAppClient;
    }

    private ApiService getApiService() {
        @NonNull
        final Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        return retrofit.create(ApiService.class);
    }

    public void getAllWeatherData(final String lat, final String lon, final ServerResponseListener<ApiData> listener){
        getApiService()
                .getWeatherData(lat, lon, Config.openWeatherMapApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiData apiData) {
                        listener.onSuccess(apiData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
