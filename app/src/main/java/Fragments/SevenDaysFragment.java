package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airmy.R;
import com.example.airmy.WeatherAdapter;
import com.example.airmy.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class SevenDaysFragment extends Fragment {

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seven_days, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);

        // Create a list of WeatherData objects (customize this based on your needs)
        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(new WeatherData("KL", "Today", "Raining", "30°C", "4 m/s", "94%", "0 mm", "30%"));
        weatherDataList.add(new WeatherData("PJ", "Today", "Cloudy", "32°C", "5 m/s", "96%", "1 mm", "32%"));
        // Add more WeatherData objects as needed

        // Create and set the adapter
        adapter = new WeatherAdapter(weatherDataList);
        recyclerView.setAdapter(adapter);

        // Optionally, you can set a layout manager (e.g., LinearLayoutManager)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
