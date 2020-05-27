package com.example.fellow_traveller.MapDirections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MapsRouteActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap map;
    private ImageButton closeButton;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    private TripInvolvedModel tripModel;
    private LatLng startDestinationToZoomOnMap;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_route);

        closeButton = findViewById(R.id.maps_route_close_button);

        myContext = getApplicationContext();

        //Parse the trip model
        tripModel = getIntent().getParcelableExtra("trip");
        place1 = new MarkerOptions().position(new LatLng(tripModel.getDestFrom().getLatitude(), tripModel.getDestFrom().getLongitude())).title(tripModel.getDestFrom().getTitle());
        place2 = new MarkerOptions().position(new LatLng(tripModel.getDestTo().getLatitude(), tripModel.getDestTo().getLongitude())).title(tripModel.getDestTo().getTitle());

        //Initialize the FromDestination to zoom when we open the map
        startDestinationToZoomOnMap = new LatLng(tripModel.getDestFrom().getLatitude(),tripModel.getDestFrom().getLongitude());


//        place1 = new MarkerOptions().position(new LatLng(40.736851, 22.920227)).title("Θεσσαλονίκη");
//        place2 = new MarkerOptions().position(new LatLng(37.983810, 23.727539)).title("Αθήνα");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps_route_fragment);
        mapFragment.getMapAsync(this);

//        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.maps_route_fragment);
//        fm.getMapAsync(this);

        if(isNetworkAvailable(myContext)) {
            String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
            new FetchURL(MapsRouteActivity.this).execute(url, "driving");
        }else
            Toast.makeText(this, "Ελέξτε την σύνδεση σας", Toast.LENGTH_SHORT).show();


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);

        //map.moveCamera(CameraUpdateFactory.newLatLng(destFrom));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(startDestinationToZoomOnMap, 9));
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.PLACE_KEY);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }
}
