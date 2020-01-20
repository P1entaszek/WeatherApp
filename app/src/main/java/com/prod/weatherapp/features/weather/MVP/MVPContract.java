package com.prod.weatherapp.features.weather.MVP;

import com.prod.weatherapp.datasource.model.ApiData;

import io.reactivex.annotations.NonNull;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public interface MVPContract {

    interface View {
        void showProgressDialog();

        void dismissProgressDialog();

        void showError(final @NonNull String error);

        void showWeatherList(final @NonNull ApiData weatherData);

        void setupView();

        void showUserOnMap(double latitude, double longitude);
    }

    interface Presenter extends Interactor.WeatherDataInteractorCallback {
        void attach(final @NonNull View view);

        void destroy();

        void initView();

        void getLastUserLocation(double latitude, double longitude);
    }

    interface Interactor {
        void getWeatherData(@NonNull final String lat, @NonNull final String lon, @NonNull final WeatherDataInteractorCallback callback);

        interface WeatherDataInteractorCallback {
            void onGetWeatherDataSuccesCallback(final @NonNull ApiData apiData);

            void onGetWeatherDataErrorCallback(final @NonNull Throwable error);
        }
    }
}
