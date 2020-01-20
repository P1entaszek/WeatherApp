
package com.prod.weatherapp.datasource.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class Sys {

    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
