package com.example.fellow_traveller.HomeFragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchDestinationsActivity;
import com.example.fellow_traveller.VerificationActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private TextView searchForTrip, welcome_user;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayout2;
    private View view;
    private GlobalClass globalClass;
    private CircleImageView circleImageView;
    private Button btnΝewΟffer, verifyButton;


    public HomeFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();


        searchForTrip = view.findViewById(R.id.FragmentHome_search_button);
        constraintLayout2 = view.findViewById(R.id.Layout3);
        welcome_user = view.findViewById(R.id.FragmentHome_user_welcome_textView);
        btnΝewΟffer = view.findViewById(R.id.FragmentHome_offer_button);

//        Calendar time = Calendar.getInstance();
//        time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
//        Date date = time.getTime();
//        Calendar cal = Calendar.getInstance();
//        TimeZone tz = cal.getTimeZone();
//        final Date currentTime = new Date();
//
//        final SimpleDateFormat sdf =
//                new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");
//        long unixTimestamp = Instant.now().getEpochSecond();
//// Give it to me in GMT time.
//        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        //System.out.println("GMT time: " + sdf.format(currentTime));
//        ZonedDateTime currentDate = ZonedDateTime.now( ZoneOffset.UTC );
//        Toast.makeText(getActivity(), "Σύστημα" + String.valueOf(System.currentTimeMillis())+ " " + unixTimestamp, Toast.LENGTH_SHORT).show();


        welcome_user.setText("Γεια σου " + globalClass.getCurrentUser().getName() + ",");
        searchForTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(getActivity(), SearchDestinationsActivity.class);
                startActivity(mainIntent);
            }
        });

        btnΝewΟffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(), NewOfferActivity.class);
                startActivity(mainIntent);
            }
        });

        return view;
    }

}