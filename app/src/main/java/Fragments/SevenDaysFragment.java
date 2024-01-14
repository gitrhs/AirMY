package Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airmy.MainActivity;
import com.example.airmy.R;
import com.example.airmy.WeatherAdapter;
import com.example.airmy.WeatherData;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SevenDaysFragment extends Fragment {

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private static String convertTimestampToDay(long timestampSeconds) {
        // Convert timestamp to milliseconds
        long timestampMillis = timestampSeconds * 1000L;

        // Create a Date object using the timestamp
        Date date = new Date(timestampMillis);

        // Create a SimpleDateFormat with GMT+0 timezone
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        // Format the date to get the day of the week
        return sdf.format(date);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seven_days, container, false);
        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        List<WeatherData> weatherDataList = new ArrayList<>();
        MainActivity mainActivity = (MainActivity) getActivity();
        String jsonString = mainActivity.getData("forecast_data", "");

        try {
            JSONObject forecastData = new JSONObject(jsonString);
            JSONObject forecastArray = forecastData.getJSONObject("forecast");
            JSONArray forecastDayArray = forecastArray.getJSONArray("forecastday");

            for (int i = 0; i < forecastDayArray.length(); i++) {
                Log.d("loopforecast", "this is work: " + i);
                JSONObject forecastDay = forecastDayArray.getJSONObject(i);

                String date = forecastDay.getString("date_epoch");
                JSONObject day = forecastDay.getJSONObject("day");
                JSONObject conditionJS = day.getJSONObject("condition");
                String conditionText = conditionJS.getString("text");

                double avgTempC = day.getDouble("avgtemp_c");
                double maxTempC = day.getDouble("maxtemp_c");
                int avgHumidity = day.getInt("avghumidity");
                double minTempC = day.getDouble("mintemp_c");
                int dailyChanceOfRain = day.getInt("daily_chance_of_rain");
                int uvNum = day.getInt("uv");
                String uvText;
                if (uvNum <=2){
                    uvText = "Low";
                } else if (uvNum <=5){
                    uvText = "Moderate";
                } else if (uvNum <= 7){
                    uvText = "High";
                } else if (uvNum <= 10){
                    uvText = "Very High";
                } else {
                    uvText = "Extreme";
                }
                // Format the information as needed
                String chanceOfRain = String.format("%d%%", dailyChanceOfRain);
                String dayOfWeek;
                if (i == 0){
                    dayOfWeek = "Today";
                } else {
                    dayOfWeek = convertTimestampToDay(Long.parseLong(date));
                }
                String condition = conditionText; // You may need to map the condition code to a readable string
                String temperature = String.format("%.1f°C", avgTempC);
                String maxTemp = String.format("%.1f°C", maxTempC);
                String humidity = String.format("%d%%", avgHumidity);
                String minTemp = String.format("%.1f°C", minTempC);
                String uv = Integer.toString(uvNum);

                // Create WeatherData object and add it to the list
                weatherDataList.add(new WeatherData(chanceOfRain, dayOfWeek, condition, temperature, maxTemp, humidity, minTemp, uv));
            }
            Log.d("loopforecast", "this is end: ");
            // Now, weatherDataList contains the desired information for each forecast day

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create and set the adapter
        adapter = new WeatherAdapter(weatherDataList);
        recyclerView.setAdapter(adapter);

        // Optionally, you can set a layout manager (e.g., LinearLayoutManager)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}