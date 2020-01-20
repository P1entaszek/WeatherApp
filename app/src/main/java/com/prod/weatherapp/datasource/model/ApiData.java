
package com.prod.weatherapp.datasource.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prod.weatherapp.datasource.model.City;
import com.prod.weatherapp.datasource.model.WeatherData;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class ApiData {

    @SerializedName("list")
    @Expose
    private java.util.List<WeatherData> list = null;
    @SerializedName("city")
    @Expose
    private City city;

    public java.util.List<WeatherData> getList() {
        return list;
    }

    public void setList(java.util.List<WeatherData> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
