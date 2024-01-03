package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsPage extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private RequestQueue requestQueue;
    TabLayout tab;
    ViewPager2 viewPage;
    ViewPageSwitcher2 switcherViewPage;
    public static final String PREF_NAME = "AirMY_SDGHeroes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        requestQueue = Volley.newRequestQueue(this);

        // Bottom nav bar
        final ImageView home = findViewById(R.id.homePage);
        final ImageView news = findViewById(R.id.newsPage);
        final ImageView healthRec = findViewById(R.id.healthRecPage);
        final ImageView userPage = findViewById(R.id.userPage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsPage.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsPage.this, NewsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });
        healthRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsPage.this, HealthRecPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });
        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsPage.this, UserProfileAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });

        String userLocation = getData("user_location", "Kuala Lumpur");
        // Update the TextView with the user_location
        TextView locationTextView = findViewById(R.id.locationTextId);
        locationTextView.setText(userLocation);


        // Tab switching ( latest and this week )
        tab = findViewById(R.id.tabSwitcher);
        viewPage = findViewById(R.id.viewPageSwitcher2);
        switcherViewPage = new ViewPageSwitcher2(this);
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









//        Toolbar toolbar = findViewById(R.id.TBMainAct);
//        setSupportActionBar(toolbar);

//        drawerLayout = findViewById(R.id.DLMain);
////        toggle = new ActionBarDrawerToggle(
////                this, drawerLayout, toolbar,
////                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavHostFragment host = (NavHostFragment)
//                getSupportFragmentManager().findFragmentById(R.id.NHFMain);
//        NavController navController = host.getNavController();
//
//        AppBarConfiguration appBarConfiguration = new
//                AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this,
//                navController, appBarConfiguration);
//
//        setupBottomNavMenu(navController);
//        setupNavMenu(navController);
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
    public String[] parseTheJson(String jsonText) {
        try {
            JSONObject jsonObject = new JSONObject(jsonText);

            // Extracting values
            String id = jsonObject.getString("id");
            String title = jsonObject.getJSONObject("fields").getString("title");

            // Navigate to the "source" array inside the "fields" object
            JSONArray sourceArray = jsonObject.getJSONObject("fields").getJSONArray("source");

            // Extract the first element from the "source" array
            String sourceName = sourceArray.getJSONObject(0).getString("name");

            // Navigate to the "date" object inside the top-level object
            String createdDate = jsonObject.getJSONObject("fields").getJSONObject("date").getString("created");

            //get country name
            JSONArray countryArray = jsonObject.getJSONObject("fields").getJSONArray("country");
            String countryName = countryArray.getJSONObject(0).getString("name");
            // Return the values in an array
            return new String[]{id, title, sourceName, createdDate, countryName};

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("parsejson error", e.toString());
            Log.e("raw data", jsonText);
        }

        // Return null or handle error case if parsing fails
        return new String[]{"id", "1", "2", "3"};
    }
    public void fetchNewsDataFromApi(String url) {
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the data from the response
                        try {
                            // Check if the "data" array exists in the response
                            if (response.has("data")) {
                                JSONArray dataArray = response.getJSONArray("data");
                                JSONObject dataContent = dataArray.getJSONObject(0);
                                String bodyData = dataContent.getJSONObject("fields").getString("body");
                                saveData("currentNewsContent", bodyData);
                            } else {
                                Log.e("newsContent", "No 'data' array found in the response");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(request1);
    }
    //    private void setupNavMenu(NavController navController){
//        NavigationView sideNav = findViewById(R.id.sideNav);
//        NavigationUI.setupWithNavController(sideNav, navController);
//    }
//
//    private void setupBottomNavMenu(NavController navController){
//        BottomNavigationView bottomNav =
//                findViewById(R.id.bottom_nav_view);
//        NavigationUI.setupWithNavController(bottomNav, navController);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.bottom_menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        try{
//            Navigation.findNavController(this,
//                    R.id.NHFMain).navigate(item.getItemId());
//            return true;
//        }
//        catch(Exception ex){
//            return super.onOptionsItemSelected(item);
//        }
//    }
    public void goToNextActivity(AppCompatActivity o) {
        Intent intent = new Intent(this, o.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
        startActivity(intent);


    }
}