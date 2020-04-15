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
import android.view.View;
import android.widget.Toast;

import com.example.fellow_traveller.HomeFragments.AccountFragment;
import com.example.fellow_traveller.HomeFragments.HomeFragment;
import com.example.fellow_traveller.HomeFragments.MessengerFragment;
import com.example.fellow_traveller.HomeFragments.NotificationFragment;
import com.example.fellow_traveller.HomeFragments.TripFragment;
import com.example.fellow_traveller.Models.ExampleJobService;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.app.job.JobInfo.NETWORK_TYPE_UNMETERED;

public class HomeActivity extends AppCompatActivity {

    private GlobalClass globalClass;
    private BottomNavigationView bottomNav;
    private static final String TAG = "ExampleJobService";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        globalClass = (GlobalClass) getApplicationContext();


        scheduleJob();

        bottomNav = findViewById(R.id.HomeActivity_bottomNavigationView);
/*        Toast.makeText(HomeActivity.this, "id " + globalClass.getCurrent_user().getId()+"\n"+
                "name " + globalClass.getCurrent_user().getName()+"\n"+
                "surname " + globalClass.getCurrent_user().getSurname(), Toast.LENGTH_SHORT).show();*/


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                    new HomeFragment()).commit();
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.bottom_nav_main:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.bottom_nav_trips:
                        selectedFragment = new TripFragment();
                        break;
                    case R.id.bottom_nav_messages:
                        selectedFragment = new MessengerFragment();
                        break;
                    case R.id.bottom_nav_notification:
                        selectedFragment = new NotificationFragment();
                        break;
                    case R.id.bottom_nav_user_info:
                        selectedFragment = new AccountFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity_frame_container,
                        selectedFragment).commit();

                return true;
            }
        });
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
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

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }
}
