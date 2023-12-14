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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class NewsPage extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    TabLayout tab;
    ViewPager2 viewPage;
    ViewPageSwitcher2 switcherViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        // Bottom nav bar
        final ImageView home = findViewById(R.id.homePageNews);
        final ImageView news = findViewById(R.id.NewsNews);
        final ImageView settings = findViewById(R.id.SettingNews);

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
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsPage.this, SettingsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });




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