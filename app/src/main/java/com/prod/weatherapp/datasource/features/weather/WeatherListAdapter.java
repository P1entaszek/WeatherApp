package com.prod.weatherapp.datasource.features.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.prod.weatherapp.R;
import com.prod.weatherapp.datasource.model.ApiData;
import com.prod.weatherapp.datasource.model.Weather;
import com.prod.weatherapp.datasource.model.WeatherData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewRow> {

    private final ApiData apiData;

    public WeatherListAdapter(ApiData apiData) {
        this.apiData = apiData;
    }

    @NonNull
    @Override
    public WeatherViewRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false);
        return new WeatherViewRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewRow holder, int position) {
        holder.city.setText(apiData.getCity().getName());
        WeatherData weatherData = apiData.getList().get(position);
        holder.date.setText(formatDate(weatherData.getDtTxt()));
        holder.temperature.setText(weatherData.getTemperatureData().getStringTemp());
        List<Weather> weather = weatherData.getWeather();
        holder.weatherDescription.setText(weather.get(0).getDescription());

    }

    private String formatDate(String dt) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH");
        Date date = null;
        try {
            date = dateFormat.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date);
    }

    @Override
    public int getItemCount() {
        return apiData.getList().size();
    }

    class WeatherViewRow extends RecyclerView.ViewHolder {
        @BindView(R.id.weatherImageView)
        CircularImageView image;
        @BindView(R.id.cityName)
        TextView city;
        @BindView(R.id.temperatureValue)
        TextView temperature;
        @BindView(R.id.dateValue)
        TextView date;
        @BindView(R.id.weatherDescription)
        TextView weatherDescription;



            public WeatherViewRow(View view) {
                super(view);
                ButterKnife.bind(this,view);
            }
        }
    }

