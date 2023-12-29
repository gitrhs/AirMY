package com.example.airmy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// WeatherAdapter.java
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherData> weatherDataList;

    public WeatherAdapter(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherData weatherData = weatherDataList.get(position);

        // Bind data to your UI elements in the ViewHolder
        holder.locationTextView.setText(weatherData.getLocation());
        holder.dayTextView.setText(weatherData.getDay());
        holder.weatherTextView.setText(weatherData.getWeather());
        // Bind other data as needed
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    // ViewHolder class
    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView locationTextView;
        TextView dayTextView;
        TextView weatherTextView;

        // Add other UI elements as needed

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            locationTextView = itemView.findViewById(R.id.locationTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            weatherTextView = itemView.findViewById(R.id.weatherTextView);
            // Initialize other UI elements
        }
    }
}
