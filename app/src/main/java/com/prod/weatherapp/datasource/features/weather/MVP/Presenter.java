package com.prod.weatherapp.datasource.features.weather.MVP;

import com.prod.weatherapp.datasource.model.ApiData;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class Presenter implements MVPContract.Presenter {
    private MVPContract.Interactor interactor = new Interactor();
    private MVPContract.View view;

    @Override
    public void attach(MVPContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void initView() {
        view.setupView();
    }

    @Override
    public void getLastUserLocation(double latitude, double longitude) {
        view.showUserOnMap(latitude, longitude);
        interactor.getWeatherData(String.valueOf(latitude), String.valueOf(longitude), this);
    }

    @Override
    public void onGetWeatherDataSuccesCallback(ApiData apiData) {
        view.showWeatherList(apiData);
        view.dismissProgressDialog();
    }

    @Override
    public void onGetWeatherDataErrorCallback(Throwable error) {
        view.showError(error.getLocalizedMessage());
        view.dismissProgressDialog();

    }
}
