package com.example.airmy;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import android.location.LocationListener;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import android.os.Looper;
import androidx.annotation.NonNull;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import Fragments.MapFragment;
import Fragments.TodayFragment;
import kotlin.text.Charsets;

//to call the api
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    TabLayout tab;
    ViewPager2 viewPage;
    ViewPageSwitcher switcherViewPage;
    private NotificationManagerCompat notifManager;

    // Declare the request queue and the shared preferences
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    //Storage File Name
    public static final String PREF_NAME = "AirMY_SDGHeroes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifManager = NotificationManagerCompat.from(this);

        // Initialize the request queue and the shared preferences
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("json_data", MODE_PRIVATE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Start location updates if permissions are granted
            //check first if there is cache "user_location"
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        // Call the API and save the JSON file in cache/session
        callAPIAndSaveJSON();
        fetchAQI();



        // Bottom nav bar
        final ImageView home = findViewById(R.id.homePage);
        final ImageView news = findViewById(R.id.newss);
        final ImageView settings = findViewById(R.id.setting);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });

        // Tabs switching " 7 day "
        tab = findViewById(R.id.tabSwitcher);
        viewPage = findViewById(R.id.viewPageSwitcher);
        switcherViewPage = new ViewPageSwitcher(this);
        viewPage.setAdapter(switcherViewPage);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // for when pulling manually the tab changes with the view.
        viewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tab.getTabAt(position).select();
            }
        });


    }
    public void saveData(String name, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public String getData(String name, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defaultValue);
    }
    //location
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            String cityName = getCityName(location);
            Log.d("Location", "City: " + cityName);
            // Do something with the city name
            //save the city
            saveData("user_location", cityName);

            // Save the full address
            String fullAddress = getFullAddress(location);
            saveData("user_full_address", fullAddress);

            String userLocation = getData("user_location", "City Error!");
            Log.d("hasilnya", "Location result: " + userLocation);
        }
    };
    private String getCityName(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getFullAddress(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder fullAddress = new StringBuilder();

                // Append address lines to the StringBuilder
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    fullAddress.append(address.getAddressLine(i)).append(", ");
                }

                // Remove the trailing comma and space
                if (fullAddress.length() > 2) {
                    fullAddress.setLength(fullAddress.length() - 2);
                }

                return fullAddress.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void fetchAQI() {
        // Initialize the Volley request queue
        String userLocation = getData("user_location", "Kuala Lumpur");
        String apiUrl = "https://airmy.mbed.cc/api/aqi.php?city="+userLocation;
        // Create the JsonObjectRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the text response
                        Log.d("AqiData", response);

                        // Parse and process the response text as needed
                        saveData("AQI", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("AqiData", "Error: " + error.toString());
                    }
                }
        );

        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }

    // Define a method to call the API and save the JSON file in cache/session
    private void callAPIAndSaveJSON() {
        // Define the API URL
        String userLocation = getData("user_location", "Kuala Lumpur");
        // Update the TextView with the user_location
        TextView locationTextView = findViewById(R.id.locationTextId);
        locationTextView.setText(userLocation);

        String url = "https://airmy.mbed.cc/api/forecast.php?district="+userLocation;

        // Create a JsonObjectRequest with the GET method
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Save the JSON data in the shared preferences
                        saveData("json_data", response.toString());

                        // Print the JSON as text for testing
                        String jsonText = response.toString();
                        try {
                            // Convert the JSON string to a JSONObject
                            JSONObject jsonObject = new JSONObject(jsonText);

                            // Get the "current" JSONObject
                            JSONObject currentObject = jsonObject.getJSONObject("current");
                            // Get the "forecast" JSONObject
                            JSONObject forecastObject = jsonObject.getJSONObject("forecast");
                            // Get the "forecastday" JSONArray
                            JSONArray forecastdayArray = forecastObject.getJSONArray("forecastday");
                            // Count the number of forecastday entries
                            int numberOfForecastDays = forecastdayArray.length() - 1;

                            // Create a new JSONObject and include the entire "current" object
                            JSONObject currentData = new JSONObject();
                            currentData.put("current", currentObject);
                            // Create a new JSONObject and include the entire "forecast" object
                            JSONObject forecastData = new JSONObject();
                            forecastData.put("forecast", forecastObject);
                            // Save the forecast and current data
                            saveData("current_data", currentData.toString());
                            saveData("forecast_data", forecastData.toString());
                            Log.d("WeatherData", "Current Data JSON: " + currentData.toString());
                            Log.d("WeatherData", "forecast Data JSON: " + forecastData.toString());

                            //count the next day prediction
                            TabLayout tabLayout = findViewById(R.id.tabSwitcher);
                            TabLayout.Tab tab2 = tabLayout.getTabAt(1);

                            if (tab2 != null) {
                                tab2.setText(numberOfForecastDays + " DAYS AHEAD");
                            }
                            //save next day prediction

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                error.printStackTrace();
            }
        }) {
            @Override
            public Cache.Entry getCacheEntry() {
                // Override the getCacheEntry method to set the cache headers and expiration time
                Cache.Entry entry = super.getCacheEntry();
                if (entry == null) {
                    entry = new Cache.Entry();
                }
                // Set the cache time to 1 hour
                long cacheTime = 60 * 60 * 1000;
                entry.ttl = System.currentTimeMillis() + cacheTime;
                entry.softTtl = System.currentTimeMillis() + cacheTime;
                return entry;
            }
        };

        // Add the JsonObjectRequest to the request queue
        requestQueue.add(jsonObjectRequest);
    }





// notification system " still work on progress don't mind it "
    public void notify(String text) {
        Notification notification = new NotificationCompat.Builder(this, NotificationManagment.channel1)
                .setSmallIcon(R.drawable.airmy_launcher_background)
                .setContentTitle("AirMy")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notifManager.notify(1, notification);
    }



}