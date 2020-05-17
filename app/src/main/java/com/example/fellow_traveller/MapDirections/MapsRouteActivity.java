package com.example.fellow_traveller.MapDirections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fellow_traveller.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsRouteActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap map;
    private Button getDirectionsButton;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_route);

        getDirectionsButton = findViewById(R.id.maps_route_button);


        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location1");
        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location2");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps_route_fragment);
        mapFragment.getMapAsync(this);
//        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.maps_route_fragment);
//        fm.getMapAsync(this);

        String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
        new FetchURL(MapsRouteActivity.this).execute(url, "driving");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);
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
}
