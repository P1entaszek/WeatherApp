package com.prod.weatherapp.features.weather;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prod.weatherapp.R;
import com.prod.weatherapp.datasource.model.ApiData;
import com.prod.weatherapp.features.weather.MVP.MVPContract;
import com.prod.weatherapp.features.weather.MVP.Presenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import net.danlew.android.joda.JodaTimeAndroid;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAppActivity extends AppCompatActivity implements OnMapReadyCallback, MVPContract.View {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private RxPermissions rxPermissions;
    private MVPContract.Presenter presenter;
    private GoogleMap map;
    private WeatherListAdapter adapter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.weatherListRecyclerView)
    RecyclerView weatherListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_app);
        ButterKnife.bind(this);
        JodaTimeAndroid.init(this);
        presenter = new Presenter();
        presenter.attach(this);
        presenter.initView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setScrollGesturesEnabled(false);
        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                            if (location != null) {
                                presenter.getLastUserLocation(location.getLatitude(), location.getLongitude());
                            } else
                                Toast.makeText(this, "no location finded", Toast.LENGTH_LONG).show();
                        });
                    } else {
                        Toast.makeText(this, "no permission granted", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWeatherList(ApiData weatherData) {
        adapter = new WeatherListAdapter(weatherData, getApplicationContext());
        weatherListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setupView() {
        rxPermissions = new RxPermissions(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        weatherListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showUserOnMap(double latitude, double longitude) {
        LatLng actualLocalization = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(actualLocalization));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocalization, 13f));
    }
}
