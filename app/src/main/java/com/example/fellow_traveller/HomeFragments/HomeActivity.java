package com.example.fellow_traveller.HomeFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.fellow_traveller.Notification.NotificationFragment;
import com.example.fellow_traveller.Models.ExampleJobService;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity {

    private GlobalClass globalClass;
    private BottomNavigationView bottomNav;
    private static final String TAG = "ExampleJobService";
    private Fragment selectedFragment = null;
    private HomeFragment homeFragment = new HomeFragment();
    private AccountFragment accountFragment = new AccountFragment();
    private MessengerFragment messengerFragment = new MessengerFragment();
    private TripFragment tripFragment = new TripFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        globalClass = (GlobalClass) getApplicationContext();




        bottomNav = findViewById(R.id.HomeActivity_bottomNavigationView);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                   homeFragment).commit();
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_main:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.bottom_nav_trips:
                        selectedFragment = new TripFragment();
                        break;
                    case R.id.bottom_nav_messages:
                        selectedFragment = new MessengerFragment();
                        break;
                    case R.id.bottom_nav_notification:
                        selectedFragment = notificationFragment;
                        break;
                    case R.id.bottom_nav_user_info:
                        selectedFragment = accountFragment;
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
            selectedFragment = homeFragment;
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                    selectedFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(1232, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

}
