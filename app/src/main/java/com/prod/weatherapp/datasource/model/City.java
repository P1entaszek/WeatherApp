
package com.prod.weatherapp.datasource.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class City {

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

}
