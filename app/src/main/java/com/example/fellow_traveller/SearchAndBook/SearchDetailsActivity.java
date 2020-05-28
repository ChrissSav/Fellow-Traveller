package com.example.fellow_traveller.SearchAndBook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.MapDirections.MapsRouteActivity;
import com.example.fellow_traveller.MapDirections.TaskLoadedCallback;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.ProfileActivity;
import com.example.fellow_traveller.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button bookButton;
    private RecyclerView mRecyclerView;
    private PassengerImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView textViewCreator, textViewRating, textViewReviews, textViewDestFrom, textViewDestTo, textViewDate,
            textViewTime, textViewSeats, textViewBags, textViewPets, textViewCar, textViewMsg, textViewPrice;
    private CircleImageView creatorUserImage;
    private Button passengersButton, showOnMapButton;
    private TripModel tripModel;
    private ImageButton imageButtonBack;
    private GlobalClass globalClass;
    private ArrayList<PassengerModel> passengersList = new ArrayList<>();
    private ConstraintLayout userLayout;
    private GoogleMap map;
    private MarkerOptions pickUpPoint;
    private LatLng zoomToThePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        globalClass = (GlobalClass) getApplicationContext();
        tripModel = getIntent().getParcelableExtra("trip");

        bookButton = findViewById(R.id.ActivitySearchDetails_book_button);
        imageButtonBack = findViewById(R.id.ActivitySearchDetails_back_button);

        creatorUserImage = findViewById(R.id.ActivitySearchDetails_user_image);
        textViewCreator = findViewById(R.id.ActivitySearchDetails_user_name);
        textViewRating = findViewById(R.id.ActivitySearchDetails_rating_tv);
        textViewReviews = findViewById(R.id.ActivitySearchDetails_reviews_tv);
        textViewPrice = findViewById(R.id.ActivitySearchDetails_price_tv);
        textViewDestFrom = findViewById(R.id.ActivitySearchDetails_from_dest_tv);
        textViewDestTo = findViewById(R.id.ActivitySearchDetails_to_dest_tv);
        textViewDate = findViewById(R.id.ActivitySearchDetails_date_tv);
        textViewTime = findViewById(R.id.ActivitySearchDetails_time_tv);
        textViewSeats = findViewById(R.id.ActivitySearchDetails_seats_tv);
        textViewBags = findViewById(R.id.ActivitySearchDetails_bags_tv);
        textViewPets = findViewById(R.id.ActivitySearchDetails_pets_tv);
        textViewCar = findViewById(R.id.ActivitySearchDetails_car_tv);
        textViewMsg = findViewById(R.id.ActivitySearchDetails_driver_message_tv);
        passengersButton = findViewById(R.id.ActivitySearchDetails_more_passengers_button);
        userLayout = findViewById(R.id.ActivitySearchDetails_user_section);
        showOnMapButton = findViewById(R.id.ActivitySearchDetails_show_map_button);

        if(tripModel.getCreatorUser().getPicture() != null)
            Picasso.get().load(tripModel.getCreatorUser().getPicture()).into(creatorUserImage);
        textViewDestFrom.setText(tripModel.getDestFrom().getTitle());
        textViewDestTo.setText(tripModel.getDestTo().getTitle());
        textViewDate.setText(tripModel.getDate());
        textViewTime.setText(tripModel.getTime());
        textViewSeats.setText(tripModel.getSeatStatus());
        textViewBags.setText(tripModel.getBagsStatus());
        textViewPets.setText(tripModel.getPetString());
        textViewCar.setText(tripModel.getCar().getBrand());
        textViewMsg.setText(tripModel.getMessage());

        //Delete 0 decimals
        if(tripModel.getPrice().intValue() == tripModel.getPrice())
            textViewPrice.setText(tripModel.getPrice().intValue() + getResources().getString(R.string.euro_symbol));
        else
            textViewPrice.setText(tripModel.getPrice() + getResources().getString(R.string.euro_symbol));

        textViewCreator.setText(tripModel.getCreatorUser().getFullName());
        textViewRating.setText(String.valueOf(tripModel.getCreatorUser().getRate()));
        textViewReviews.setText(String.valueOf(tripModel.getCreatorUser().getReviews()));
        passengersList = tripModel.getPassengers();



        //Get the Map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.ActivitySearchDetails_map_fragment);
        mapFragment.getMapAsync(this);

        pickUpPoint = new MarkerOptions().position(new LatLng(tripModel.getDestFrom().getLatitude(), tripModel.getDestFrom().getLongitude())).title(tripModel.getDestFrom().getTitle());
        zoomToThePoint = new LatLng(tripModel.getDestFrom().getLatitude(),tripModel.getDestFrom().getLongitude());

//        UserBaseModel userBaseModel = new UserBaseModel(1, "Tyler",   "Joseph", 4.7, 34, "default" );
//        PassengerModel passengerModel = new PassengerModel(userBaseModel, 3, true);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);

        try{
            Log.i("passengersList",passengersList.size()+"  |   "+tripModel.getPassengers().size());

        }catch (Exception e ){
            Log.i("passengersList",e.toString());

        }

        mRecyclerView = findViewById(R.id.ActivitySearchDetails_passenger_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new PassengerImageAdapter(passengersList, this.getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        passengersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, SearchPassengersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("tripPassengers", tripModel.getPassengers());
                mainIntent.putExtras(bundle);
                //mainIntent.putExtra("tripPassengers",  tripModel.getPassengers());
                startActivity(mainIntent);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, BookActivity.class);
                mainIntent.putExtra("trip", tripModel);
                startActivity(mainIntent);
            }
        });
        mAdapter.setOnItemClickListener(new PassengerImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, ProfileActivity.class);
                mainIntent.putExtra("ThisUser", passengersList.get(position).getUser());
                startActivity(mainIntent);
            }
        });
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, ProfileActivity.class);
                mainIntent.putExtra("ThisUser", tripModel.getCreatorUser());
                startActivity(mainIntent);
            }
        });
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, MapsRouteActivity.class);
                mainIntent.putExtra("trip", tripModel);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addCircle(new CircleOptions()
                .center(new LatLng(tripModel.getDestFrom().getLatitude(), tripModel.getDestFrom().getLongitude()))
                .radius(400)
                .strokeColor(Color.argb(20, 255, 0,0))
                .fillColor(Color.argb(50, 255, 0,0)));
        //map.addMarker(pickUpPoint);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(zoomToThePoint, 15));
    }
}















