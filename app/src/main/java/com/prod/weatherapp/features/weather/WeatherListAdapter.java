package com.prod.weatherapp.features.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.prod.weatherapp.R;
import com.prod.weatherapp.datasource.model.ApiData;
import com.prod.weatherapp.datasource.model.Weather;
import com.prod.weatherapp.datasource.model.WeatherData;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Piotr Jaszczurowski on 20.01.2020.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewRow> {

    private final ApiData apiData;
    private final Context context;

    public WeatherListAdapter(ApiData apiData, Context context) {
        this.apiData = apiData;
        this.context = context;
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
        //TODO Powydzielać do metod
        WeatherData weatherData = apiData.getList().get(position);
        DateTimeFormatter currentFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        DateTimeFormatter convertedOutputFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDateTime localDateTime = LocalDateTime.parse(weatherData.getDtTxt(), currentFormatter);
        holder.date.setText(localDateTime.format(convertedOutputFormatter));
        DecimalFormat df = new DecimalFormat("#");
        String temperature = String.valueOf(df.format(weatherData.getTemperatureData().getTemp()));
        holder.temperature.setText(temperature + " " +  context.getString(R.string.celsius_symbol));
        List<Weather> weather = weatherData.getWeather();
        setImageResource(weather.get(0).getMain(), holder);
        holder.weatherDescription.setText(weather.get(0).getDescription());

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
            ButterKnife.bind(this, view);
        }
    }

    private void setImageResource(final String weatherCondition, final WeatherViewRow holder){
        if(weatherCondition.equalsIgnoreCase("clouds")) Glide.with(context).load(R.drawable.cloud).into(holder.image);
        if(weatherCondition.equalsIgnoreCase("rain")) Glide.with(context).load(R.drawable.rain).into(holder.image);
        if(weatherCondition.equalsIgnoreCase("clear")) Glide.with(context).load(R.drawable.sun).into(holder.image);
        if(weatherCondition.equalsIgnoreCase("snow")) Glide.with(context).load(R.drawable.snow).into(holder.image);
        if(weatherCondition.equalsIgnoreCase("extreme")) Glide.with(context).load(R.drawable.extreme).into(holder.image);
    }
}

