
package com.prod.weatherapp.datasource.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class TemperatureData {

    @SerializedName("temp")
    @Expose
    private Double temp;

    public Double getTemp() {
        return temp;
    }

    public String getStringTemp(){
        return String.valueOf(temp);
    }
}
