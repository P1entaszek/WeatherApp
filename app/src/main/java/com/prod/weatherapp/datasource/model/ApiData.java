
package com.prod.weatherapp.datasource.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class ApiData {

    @SerializedName("list")
    @Expose
    private java.util.List<WeatherData> list = null;

    public void setList(List<WeatherData> list) {
        this.list = list;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @SerializedName("city")
    @Expose
    private City city;

    public java.util.List<WeatherData> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }
}
