package com.prod.weatherapp;

import com.prod.weatherapp.datasource.ServerResponseListener;
import com.prod.weatherapp.datasource.model.ApiData;
import com.prod.weatherapp.datasource.model.WeatherData;
import com.prod.weatherapp.datasource.retrofit.ApiService;
import com.prod.weatherapp.datasource.retrofit.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private DateTimeFormatter currentFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    public static WeatherAppClient getWeatherAppClient() {
        if (weatherAppClient == null) {
            weatherAppClient = new WeatherAppClient();
        }
        return weatherAppClient;
    }

    private ApiService getApiService() {
        @NonNull final Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        return retrofit.create(ApiService.class);
    }

    public void getAllWeatherData(final String lat, final String lon, final ServerResponseListener<ApiData> listener) {
        getApiService()
                .getWeatherData(lat, lon, "metric", Config.openWeatherMapApiKey)
                .subscribeOn(Schedulers.io())
                .map(apiData -> {
                    ApiData finalApiData = new ApiData();
                    List<WeatherData> finalWeatherList = new ArrayList<>();
                    List<WeatherData> weatherData = apiData.getList();
                    LocalDateTime localDateTimeFirstDay = LocalDateTime.parse(weatherData.get(0).getDtTxt(), currentFormatter);
                    LocalDateTime localDateTimeSecondDay = localDateTimeFirstDay.plusDays(1);
                    LocalDateTime localDateTimeThirdDay = localDateTimeFirstDay.plusDays(2);
                    finalApiData.setCity(apiData.getCity());
                    for (WeatherData weatherDataItem : weatherData) {
                        if (LocalDateTime.parse(weatherDataItem.getDtTxt(), currentFormatter).equals(localDateTimeFirstDay))
                            finalWeatherList.add(weatherDataItem);
                        if (LocalDateTime.parse(weatherDataItem.getDtTxt(), currentFormatter).equals(localDateTimeSecondDay))
                            finalWeatherList.add(weatherDataItem);
                        if (LocalDateTime.parse(weatherDataItem.getDtTxt(), currentFormatter).equals(localDateTimeThirdDay))
                            finalWeatherList.add(weatherDataItem);
                    }
                    finalApiData.setList(finalWeatherList);
                    return finalApiData;
                })
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
