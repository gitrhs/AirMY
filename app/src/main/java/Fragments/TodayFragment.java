package Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.airmy.MainActivity;
import com.example.airmy.R;
import com.example.airmy.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;


public class TodayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView leafletWebView;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Obtain a reference to the MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        Button donateBttnForFlood = (Button) view.findViewById(R.id.donateBttnForFlood);

        donateBttnForFlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.redcrescent.org.my");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        leafletWebView = view.findViewById(R.id.leafletWebView);

        // Enable JavaScript (required for Leaflet)
        WebSettings webSettings = leafletWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable Geolocation in WebView
        webSettings.setGeolocationEnabled(true);

        // Set a WebViewClient to handle page navigation within the WebView
        leafletWebView.setWebViewClient(new WebViewClient());

        // Set a WebChromeClient to handle permission requests for Geolocation
        leafletWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // Request permission to use Geolocation
                callback.invoke(origin, true, false);
            }
        });

        // Load Leaflet map HTML file
        //get user full location user_full_address
        String fullAddress = mainActivity.getData("user_full_address", "Kuala Lumpur");
        leafletWebView.loadUrl("https://airmy.mbed.cc/map.php?location="+fullAddress);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        // Apply layout parameters to the WebView
        leafletWebView.setLayoutParams(layoutParams);

        // Find the mapContainer FrameLayout
        FrameLayout mapContainer = view.findViewById(R.id.mapContainer);

        // Create and add the ContentFragment
        MapFragment mapFrag = new MapFragment();
        getChildFragmentManager().beginTransaction()
                .replace(mapContainer.getId(), mapFrag)
                .commit();

        //get "current" data from storage
        String jsonString = mainActivity.getData("current_data", "");
        if (!jsonString.isEmpty()){
            try {

                // Convert the JSON string to a JSONObject
                JSONObject jsonObject = new JSONObject(jsonString);

                // Get the "current" JSONObject
                JSONObject currentObject = jsonObject.getJSONObject("current");

                // Get the value of "temp_c"
                int tempC = currentObject.getInt("temp_c");
                //update the temp_c at the screen
                TextView temptdyCard = view.findViewById(R.id.temptdyCard);
                temptdyCard.setText(tempC+"°C");

                //get the value of "unknownperctdyCard" / "humidity"
                int humidity = currentObject.getInt("humidity");
                //update humidity at the screen
                TextView unknownperctdyCard = view.findViewById(R.id.unknownperctdyCard);
                unknownperctdyCard.setText(humidity+"%");

                //get the value of "windspeedtdyCard" / "wind_kph"
                double wind_kph = currentObject.getDouble("wind_kph");
                //update wind_kph at the screen
                TextView windspeedtdyCard = view.findViewById(R.id.windspeedtdyCard);
                windspeedtdyCard.setText(wind_kph+" K/H");

                //get "userLocation"
                String userLocation = mainActivity.getData("user_location", "");
                TextView textuserLocation = view.findViewById(R.id.userLocation);
                textuserLocation.setText(userLocation);

                //get condition condition->text and save it on "weathertdyCard"
                JSONObject conditionObject = currentObject.getJSONObject("condition");
                String weatherCondition = conditionObject.getString("text");
                TextView weathertdyCard = view.findViewById(R.id.weathertdyCard);
                weathertdyCard.setText(weatherCondition);




                //FLTemptday -> feelslike_c
                int feelslike_c = currentObject.getInt("feelslike_c");
                TextView FLTemptday = view.findViewById(R.id.FLTemptday);
                FLTemptday.setText(feelslike_c+"°C");
                //get "rainChance"
                String forecastString = mainActivity.getData("forecast_data", "");

                try {
                    JSONObject forecastData = new JSONObject(forecastString);
                    JSONObject forecastArray = forecastData.getJSONObject("forecast");
                    JSONArray forecastDayArray = forecastArray.getJSONArray("forecastday");

                    JSONObject forecastDay = forecastDayArray.getJSONObject(0);
                    JSONObject day = forecastDay.getJSONObject("day");

                    double maxTempC = day.getDouble("maxtemp_c");
                    double minTempC = day.getDouble("mintemp_c");
                    int dailyChanceOfRain = day.getInt("daily_chance_of_rain");
                    int uvNum = currentObject.getInt("uv");
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
                    //save the rainChance
                    TextView rainChance = view.findViewById(R.id.rainChance);
                    rainChance.setText(String.format("%d%%", dailyChanceOfRain));

                    //highTempTdy
                    TextView highTempTdy = view.findViewById(R.id.highTempTdy);
                    highTempTdy.setText(String.format("%.1f°C", maxTempC));

                    //lowTempTdy
                    TextView lowTempTdy = view.findViewById(R.id.lowTempTdy);
                    lowTempTdy.setText(String.format("%.1f°C", minTempC));

                    //uvCard
                    TextView uvCard = view.findViewById(R.id.uvCard);
                    uvCard.setText(uvText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //count Air Quality Index "airqualityNumberToday"
                String AQI = mainActivity.getData("AQI", "-");
                TextView airqualityNumberToday = view.findViewById(R.id.airqualityNumberToday);
                airqualityNumberToday.setText(AQI);
                String AQIDesc;
                String AQIText;
                String maskStatus = "Mask recommended";
                int AQINum;

                // Check if AQI is a valid integer
                if (!AQI.isEmpty() && AQI.matches("\\d+")) {
                    AQINum = Integer.parseInt(AQI);
                    if (AQINum >= 0 && AQINum <= 50) {
                        AQIText = "Good";
                        AQIDesc = "Air quality is good, and air pollution poses little or no risk.";
                    } else if (AQINum <= 100) {
                        AQIText = "Moderate";
                        AQIDesc = "Okay, but sensitive individuals may experience minor health effects.";
                    } else if (AQINum <= 150) {
                        AQIText = "Unhealthy for Sensitive Groups";
                        AQIDesc = "Members of sensitive groups may experience health effects. The general public is less likely to be affected.";
                    } else if (AQINum <= 200) {
                        AQIText = "Unhealthy";
                        maskStatus = "Mask required";
                        AQIDesc = "Everyone may begin to experience health effects, and members of sensitive groups may experience more serious health effects.";
                    } else if (AQINum <= 300) {
                        AQIText = "Very Unhealthy";
                        maskStatus = "Mask required";
                        AQIDesc = "Health alert: everyone may experience more serious health effects.";
                    } else if (AQINum <= 500) {
                        AQIText = "Hazardous";
                        maskStatus = "Mask required";
                        AQIDesc = "Health warnings of emergency conditions; the entire population is more likely to be affected.";
                    } else {
                        AQIText = "Invalid AQI Value";
                        maskStatus = "-";
                        AQIDesc = "Unknown AQI category";
                    }
                } else {
                    AQIText = "Invalid AQI Value";
                    maskStatus = "-";
                    AQIDesc = "Unknown AQI category";
                }



                //show the text on "airqualityTextRateToday"
                TextView airqualityTextRateToday = view.findViewById(R.id.airqualityTextRateToday);
                airqualityTextRateToday.setText(AQIText);
                //show the desc on "healthEffictsToday"
                TextView healthEffictsToday = view.findViewById(R.id.healthEffictsToday);
                healthEffictsToday.setText(AQIDesc);
                //show the maskStatus on maskRequiry
                TextView maskRequiry = view.findViewById(R.id.maskRequiry);
                maskRequiry.setText(maskStatus);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

}