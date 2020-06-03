package com.example.fellow_traveller.HomeFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fellow_traveller.Notification.NotificationFragment;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

   // private GlobalClass globalClass;
    private BottomNavigationView bottomNav;
    private static final String TAG = "ExampleJobService";
    private Fragment selectedFragment = null;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment = new NotificationFragment();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //globalClass = (GlobalClass) getApplicationContext();
        homeFragment = new HomeFragment();


        bottomNav = findViewById(R.id.HomeActivity_bottomNavigationView);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                    homeFragment).commit();
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //globalClass = (GlobalClass) getApplicationContext();
                switch (item.getItemId()) {
                    case R.id.bottom_nav_main:
                        if (!(selectedFragment instanceof HomeFragment))
                            selectedFragment = new HomeFragment();
                        break;
                    case R.id.bottom_nav_trips:
                        if (!(selectedFragment instanceof TripFragment))
                            selectedFragment = new TripFragment();
                        break;
                    case R.id.bottom_nav_messages:
                        if (!(selectedFragment instanceof MessengerFragment))
                            selectedFragment = new MessengerFragment();
                        break;
                    case R.id.bottom_nav_notification:
                        if (!(selectedFragment instanceof NotificationFragment))
                            selectedFragment = notificationFragment;
                        break;
                    case R.id.bottom_nav_user_info:
                        if (!(selectedFragment instanceof AccountFragment))
                            selectedFragment = new AccountFragment();
                        break;
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                        selectedFragment).commit();

                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (!(selectedFragment instanceof HomeFragment)) {
            bottomNav.setSelectedItemId(R.id.bottom_nav_main);
            selectedFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                    selectedFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

}
