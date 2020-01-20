package com.prod.weatherapp.datasource;


import androidx.annotation.Nullable;

/**
 * Created by jaszczurowskip on 03.12.2018
 */
public interface ServerResponseListener<T> {

    void onSuccess(final @Nullable T response);

    void onError(final @Nullable Throwable error);
}
