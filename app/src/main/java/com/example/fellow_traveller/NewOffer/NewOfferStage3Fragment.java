package com.example.fellow_traveller.NewOffer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.Adapters.CarAdapter;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Settings.AddCarSettingsActivity;

import java.util.ArrayList;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.fellow_traveller.Util.SomeMethods.createSnackBar;


public class NewOfferStage3Fragment extends Fragment {
    private final String CLICK_COLOR = "#1C1C1C";
    private final String TITLE_PET = "Επέλεξε ...";
    private final String TITLE_SEAT = "1";
    private final String TITLE_CAR = "Διάλεξε αυτοκίνητο ...";
    private final String TITLE_BAGS = "0";

    private View view;
    private Button buttonPet, buttonCar;
    //Backup
    private String petTitle = TITLE_PET;
    private String seatTitle = TITLE_SEAT;
    private String carTitle = TITLE_CAR;
    private String bagsTitle = TITLE_BAGS;

    //Recycle
    private RecyclerView mRecyclerView;
    private CarAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CarModel> mExampleList;

    private TextView textViewBags, textViewSeats;
    private ImageButton imageButtonIncreaseSeats, imageButtonIncreaseBags;
    private ImageButton imageButtonDecreaseSeats, imageButtonDecreaseBags;


    private GlobalClass globalClass;

    private CarModel currentCar;

    public NewOfferStage3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage3, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        buttonPet = view.findViewById(R.id.NewOfferStage3Fragment_button_pet);
        buttonCar = view.findViewById(R.id.NewOfferStage3Fragment_button_car);

        //Seats
        textViewSeats = view.findViewById(R.id.NewOfferStage3Fragment_seats_value_tv);
        imageButtonIncreaseSeats = view.findViewById(R.id.NewOfferStage3Fragment_plus_button_seats);
        imageButtonDecreaseSeats = view.findViewById(R.id.NewOfferStage3Fragment_minus_button_seats);

        //Bags
        textViewBags = view.findViewById(R.id.NewOfferStage3Fragment_bags_value_tv);
        imageButtonIncreaseBags = view.findViewById(R.id.NewOfferStage3Fragment_plus_button_bags);
        imageButtonDecreaseBags = view.findViewById(R.id.NewOfferStage3Fragment_minus_button_bags);





        // Seat OnClickListener
        imageButtonIncreaseSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase( textViewSeats);
            }
        });

        imageButtonDecreaseSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textViewSeats,1);
            }
        });

        // Bag OnClickListener

        imageButtonIncreaseBags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase( textViewBags);
            }
        });

        imageButtonDecreaseBags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textViewBags,0);
            }
        });



        textViewBags.setText(bagsTitle);
        textViewSeats.setText(seatTitle);
        buttonPet.setText(petTitle);
        buttonCar.setText(carTitle);

        //Text Color
        if (buttonPet.getText() != TITLE_PET) {
            buttonPet.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (buttonCar.getText() != TITLE_CAR) {
            buttonCar.setTextColor(Color.parseColor(CLICK_COLOR));
        }




        buttonCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogForCar();
            }
        });

        buttonPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPet.setTextColor(Color.parseColor(CLICK_COLOR));
                if (buttonPet.getText().equals(TITLE_PET) || buttonPet.getText().equals("Δεν επιτρέπω")) {
                    buttonPet.setText("Επιτρέπω");
                    return;
                }
                if (buttonPet.getText().equals("Επιτρέπω")) {
                    buttonPet.setText("Δεν επιτρέπω");
                    return;
                }


            }
        });
        return view;
    }


    public void openDialogForCar() {

        final Dialog dialog = new Dialog(getActivity(), R.style.ThemeDialogNew);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_car);
        dialog.setCancelable(true);

        Button button = dialog.findViewById(R.id.choose_car_button_add_car);


        getCars(dialog);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Intent intent = new Intent(getActivity(), AddCarSettingsActivity.class);
                startActivityForResult(intent, 1);


            }
        });


    }

    public void buildRecyclerViewForCar(final Dialog dialog) {
        mRecyclerView = dialog.findViewById(R.id.choose_car_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CarAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                buttonCar.setTextColor(Color.parseColor(CLICK_COLOR));
                buttonCar.setText(mExampleList.get(position).getDescription());
                currentCar = mExampleList.get(position);
                dialog.dismiss();


            }
        });
    }







    public String toString() {
        return "NewOfferStage3Fragment";
    }

    public int getRank() {
        return 3;
    }

    public void Increase(TextView textView) {
        int current_num = Integer.parseInt(textView.getText().toString());
        textView.setText((current_num + 1) + "");

    }

    public void Decrease(TextView textView,int min) {
        int current_num = Integer.parseInt(textView.getText().toString());
        if (current_num > min)
            textView.setText((current_num - 1) + "");
    }

    @Override
    public void onDestroy() {
        petTitle = buttonPet.getText().toString();
        seatTitle = textViewSeats.getText().toString();
        carTitle = buttonCar.getText().toString();
        bagsTitle = textViewBags.getText().toString();
        super.onDestroy();
    }

    public boolean validateFragment() {
        if (checkCar() && checkSeats() && checkBags() && checkPet()) {
            return true;
        }
        return false;
    }


    public Boolean checkCar() {
        if (buttonCar.getText().equals(TITLE_CAR)) {
            createSnackBar(view, "Παρακαλω καταχωρήστε το όχημα σας");
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkPet() {
        if (buttonPet.getText().equals(TITLE_PET)) {
            createSnackBar(view, "Παρακαλω καταχωρήστε αν δεχεστε κατοικίδια");
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkSeats() {
        try {
            int res = Integer.parseInt(textViewSeats.getText().toString());
            if (res < 1) {
                createSnackBar(view, "Οι θεσεις πρεπει να ειναι τουλαχιστον μια");
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            createSnackBar(view, "Υπαρχει λαθος στην καταχωρηση των θεσεων");
            return false;
        }
    }

    public Boolean checkBags() {
        try {
            int res = Integer.parseInt(textViewBags.getText().toString());
            return true;

        } catch (NumberFormatException e) {
            createSnackBar(view, "Υπαρχει λαθος στην καταχωρηση των αποσκευων") ;
            return false;
        }
    }


    public String getSeats() {
        return textViewSeats.getText().toString();
    }

    public String getPets() {
        return buttonPet.getText().toString();
    }

    public Boolean getPetsBoolean() {

        return buttonPet.getText().toString().equals("Επιτρέπω");
    }

    public String getCar() {
        return buttonCar.getText().toString();
    }

    public String getBags() {
        return textViewBags.getText().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                CarModel car = data.getParcelableExtra("car");
                currentCar = car;
                buttonCar.setTextColor(Color.parseColor(CLICK_COLOR));
                buttonCar.setText(car.getDescription());


            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
    }

    public CarModel getCarObject() {
        return currentCar;
    }

    public void getCars(final Dialog dialog) {

        new FellowTravellerAPI(globalClass).getCars(new UserCarsCallBack() {
            @Override
            public void onSuccess(ArrayList<CarModel> carList) {
                mExampleList = new ArrayList<>();
                mExampleList = carList;
                buildRecyclerViewForCar(dialog);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }


}
