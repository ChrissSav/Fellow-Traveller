package com.example.fellow_traveller.Trips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.MapDirections.MapsRouteActivity;
import com.example.fellow_traveller.ProfileActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.PassengerImageAdapter;
import com.example.fellow_traveller.SearchAndBook.SearchDestinationsActivity;
import com.example.fellow_traveller.SearchAndBook.SearchDetailsActivity;
import com.example.fellow_traveller.SearchAndBook.SearchLocationActivity;
import com.example.fellow_traveller.SearchAndBook.SearchPassengersActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TripPageDriverActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView QRImage;
    public static TextView QRResultTextView;
    private Button scanButton;
    private ImageButton imageButtonBack;
    private final int demensionPixels = 700;
    private boolean isCompleted, isDriver;
    private TripModel tripModel;
    private TextView textViewCreator, textViewRating, textViewReviews, textViewDestFrom, textViewDestTo, textViewDate,
            textViewTime, textViewSeats, textViewBags, textViewPets, textViewCar, textViewMsg, textViewPrice, textViewColor, textViewPlate;
    private CircleImageView creatorUserImage;
    private Button passengersButton, showOnMapButton;
    private ConstraintLayout userLayout;
    private RecyclerView mRecyclerView;
    private PassengerImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PassengerModel> passengersList = new ArrayList<>();
    private long oneHour = 60*60;
    private GoogleMap map;
    private MarkerOptions pickUpPoint;
    private LatLng zoomToThePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page_driver);
        QRImage = findViewById(R.id.ActivityTripPageDriver_qr_image);
        //QRResultTextView = findViewById(R.id.ActivityTripPageDriver_qr_result_textView);
        scanButton = findViewById(R.id.ActivityTripPageDriver_scan_button);


        //TextView Initialization
        creatorUserImage = findViewById(R.id.ActivityTripPageDriver_user_image);
        textViewCreator = findViewById(R.id.ActivityTripPageDriver_user_name);
        textViewRating = findViewById(R.id.ActivityTripPageDriver_rating_tv);
        textViewReviews = findViewById(R.id.ActivityTripPageDriver_reviews_tv);
        //textViewPrice = findViewById(R.id.ActivityTripPageDriver_price_tv);
        textViewDestFrom = findViewById(R.id.ActivityTripPageDriver_from_dest_tv);
        textViewDestTo = findViewById(R.id.ActivityTripPageDriver_to_dest_tv);
        textViewDate = findViewById(R.id.ActivityTripPageDriver_date_tv);
        textViewTime = findViewById(R.id.ActivityTripPageDriver_time_tv);
        textViewSeats = findViewById(R.id.ActivityTripPageDriver_seats_tv);
        textViewBags = findViewById(R.id.ActivityTripPageDriver_bags_tv);
        textViewPets = findViewById(R.id.ActivityTripPageDriver_pets_tv);
        textViewCar = findViewById(R.id.ActivityTripPageDriver_car_tv);
        textViewColor = findViewById(R.id.ActivityTripPageDriver_color_tv);
        textViewPlate = findViewById(R.id.ActivityTripPageDriver_plate_tv);
        textViewMsg = findViewById(R.id.ActivityTripPageDriver_driver_message_tv);
        passengersButton = findViewById(R.id.ActivityTripPageDriver_more_passengers_button);
        userLayout = findViewById(R.id.ActivityTripPageDriver_user_section);
        showOnMapButton = findViewById(R.id.ActivityTripPageDriver_show_map_button);
        imageButtonBack = findViewById(R.id.ActivityTripPageDriver_back_button);

        createQR();

        Bundle bundle = getIntent().getExtras();
        isCompleted = bundle.getBoolean("isCompleted");
        isDriver = bundle.getBoolean("isDriver");
        tripModel = getIntent().getParcelableExtra("trip");

        //Get the Map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.ActivityTripPageDriver_map_fragment);
        mapFragment.getMapAsync(this);

        pickUpPoint = new MarkerOptions().position(new LatLng(tripModel.getDestFrom().getLatitude(), tripModel.getDestFrom().getLongitude())).title(tripModel.getDestFrom().getTitle());
        zoomToThePoint = new LatLng(tripModel.getDestFrom().getLatitude(),tripModel.getDestFrom().getLongitude());

        //Set the values
        textViewDestFrom.setText(tripModel.getDestFrom().getTitle());
        textViewDestTo.setText(tripModel.getDestTo().getTitle());
        textViewDate.setText(tripModel.getDate());
        textViewTime.setText(tripModel.getTime());
        textViewSeats.setText(tripModel.getSeatStatus());
        textViewBags.setText(tripModel.getBagsStatus());
        textViewPets.setText(tripModel.getPetString());
        textViewCar.setText(tripModel.getCar().getBrand());
        textViewColor.setText(tripModel.getCar().getColor());
        textViewPlate.setText(tripModel.getCar().getPlate());
        textViewCreator.setText(tripModel.getCreatorUser().getFullName());
        textViewRating.setText(String.valueOf(tripModel.getCreatorUser().getRate()));
        textViewReviews.setText(String.valueOf(tripModel.getCreatorUser().getReviews()));
        if(tripModel.getCreatorUser().getPicture() != null)
            Picasso.get().load(tripModel.getCreatorUser().getPicture()).into(creatorUserImage);





        if (tripModel.getMessage().equals(""))
            textViewMsg.setText("Ο οδηγός δεν έχει αναφέρει κάποιο συγκεκριμένο μήνυμα");
        else
            textViewMsg.setText(tripModel.getMessage());

        passengersList = tripModel.getPassengers();

        //Build the passengers recycler view
        mRecyclerView = findViewById(R.id.ActivityTripPageDriver_passenger_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new PassengerImageAdapter(passengersList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        //Display the settings for the driver or the user etc if is driver and the trip is not completed, it will show the QR Code
        if (isCompleted){
            QRImage.setVisibility(View.GONE);
            scanButton.setVisibility(View.GONE);
        }else{
            if(isDriver){
                QRImage.setVisibility(View.VISIBLE);
                scanButton.setVisibility(View.GONE);
            }else{
                QRImage.setVisibility(View.GONE);
                scanButton.setVisibility(View.VISIBLE);
            }
        }

        //Button listeners
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        passengersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(TripPageDriverActivity.this, SearchPassengersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("tripPassengers", tripModel.getPassengers());
                mainIntent.putExtras(bundle);
                //mainIntent.putExtra("tripPassengers",  tripModel.getPassengers());
                startActivity(mainIntent);
            }
        });
        mAdapter.setOnItemClickListener(new PassengerImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mainIntent = new Intent(TripPageDriverActivity.this, ProfileActivity.class);
                mainIntent.putExtra("ThisUser", passengersList.get(position).getUser());
                startActivity(mainIntent);
            }
        });
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(TripPageDriverActivity.this, ProfileActivity.class);
                mainIntent.putExtra("ThisUser", tripModel.getCreatorUser());
                startActivity(mainIntent);
            }
        });
        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(TripPageDriverActivity.this, MapsRouteActivity.class);
                mainIntent.putExtra("trip", tripModel);
                startActivity(mainIntent);
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(TripPageDriverActivity.this, ScanQRCodeActivity.class);
                startActivityForResult(mainIntent, 1);
            }
        });


    }
    //We Build the QR Code in case the user is the driver
    public void createQR(){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            //The first value is the string we want to make QR for
            BitMatrix bitMatrix = qrCodeWriter.encode("ft764535", BarcodeFormat.QR_CODE, demensionPixels, demensionPixels);
            Bitmap bitmap = Bitmap.createBitmap(demensionPixels, demensionPixels, Bitmap.Config.RGB_565);

            for(int x = 0; x < demensionPixels; x++){
                for(int y = 0; y < demensionPixels; y++){
                    bitmap.setPixel( x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            QRImage.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //We get the scan result in case user is a passenger
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String resultQR = data.getStringExtra("resultFromQRCode");
                Toast.makeText(TripPageDriverActivity.this, resultQR, Toast.LENGTH_SHORT).show();
                checkResultFromQR(resultQR
                );

                //Toast.makeText(getContext(),predictionsModelDestFrom.toString(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(),predictionsModelDestTo.toString(), Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
    }

    public void checkResultFromQR(String result){

        //Current time must be between one hour +- of the trip's departure time
        if((tripModel.getTimestamp() > (System.currentTimeMillis()/1000) - oneHour) && (tripModel.getTimestamp() < (System.currentTimeMillis()/1000) + oneHour)){
            if(result.equals("ft764535"))
                Toast.makeText(this, "Ευχαριστούμε πολύ... Καλό σας ταξίδι!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Δεν σκανάρατε έγκυρο κωδικό ταξιδιού", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Δεν είναι ακόμα η ώρα του ταξιδιού. Ο κωδικός θα γίνει ενεργός λίγο πριν την ώρα αναχώρησης του ταξιδιού", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(pickUpPoint);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(zoomToThePoint, 18));
    }
}
