package com.example.fellow_traveller.NewOffer;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class NewOfferStage3Fragment extends Fragment {
    private final String CLICK_COLOR = "#1C1C1C";
    private final String TITLE_PET = "Επέλεξε ...";
    private final String TITLE_SEAT = "Θέσεις ...";
    private final String TITLE_CAR = "Διάλεξε αυτοκίνητο ...";
    private final String TITLE_BAGS = "Αποσκεύες ...";

    private View view;
    private Button buttonSeats, buttonPet, buttonCar, button_bags;
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
        buttonSeats = view.findViewById(R.id.NewOfferStage3Fragment_button_seat);
        buttonPet = view.findViewById(R.id.NewOfferStage3Fragment_button_pet);
        buttonCar = view.findViewById(R.id.NewOfferStage3Fragment_button_car);
        button_bags = view.findViewById(R.id.NewOfferStage3Fragment_button_bags);


        buttonPet.setText(petTitle);
        buttonSeats.setText(seatTitle);
        buttonCar.setText(carTitle);
        button_bags.setText(bagsTitle);


        //Text Color
        if (buttonPet.getText() != TITLE_PET) {
            buttonPet.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (button_bags.getText() != TITLE_BAGS) {
            button_bags.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (buttonCar.getText() != TITLE_CAR) {
            buttonCar.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (buttonSeats.getText() != TITLE_SEAT) {
            buttonSeats.setTextColor(Color.parseColor(CLICK_COLOR));
        }


        buttonSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogForSeats();
            }
        });

        button_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogForBags();
            }
        });


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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.choose_car, null);
        Button button = mView.findViewById(R.id.choose_car_button_add_car);


        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();

        getCars(mView, dialog);
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

    public void buildRecyclerViewForCar(View view, final AlertDialog dialog) {
        mRecyclerView = view.findViewById(R.id.choose_car_recyclerView);
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

    public void openDialogForSeats() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.number_choose, null);
        Button button = mView.findViewById(R.id.choose_num_button);
        ImageButton increase = mView.findViewById(R.id.choose_num_imageButton_plus);
        ImageButton decrease = mView.findViewById(R.id.choose_num_imageButton_minus);
        final TextView textView_number = mView.findViewById(R.id.choose_num_textView_number);
        TextView textView_title = mView.findViewById(R.id.choose_num_textView_title);
        if (!buttonSeats.getText().equals(TITLE_SEAT)) {
            textView_number.setText(buttonSeats.getText().toString());
        }

        textView_title.setText("Καθόρισε τον αριθμό των θέσεων");
        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                buttonSeats.setTextColor(Color.parseColor(CLICK_COLOR));
                buttonSeats.setText(textView_number.getText().toString());
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
            }
        });

    }

    public void openDialogForBags() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.number_choose, null);
        Button button = mView.findViewById(R.id.choose_num_button);
        ImageButton increase = mView.findViewById(R.id.choose_num_imageButton_plus);
        ImageButton decrease = mView.findViewById(R.id.choose_num_imageButton_minus);
        final TextView textView_number = mView.findViewById(R.id.choose_num_textView_number);
        TextView textView_title = mView.findViewById(R.id.choose_num_textView_title);
        if (!button_bags.getText().equals(TITLE_BAGS)) {
            textView_number.setText(button_bags.getText().toString());
        }

        textView_title.setText("Καθόρισε τον αριθμό των αποσκεύων");
        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                button_bags.setTextColor(Color.parseColor(CLICK_COLOR));
                button_bags.setText(textView_number.getText().toString());

            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
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

    public void Decrease(TextView textView) {
        int current_num = Integer.parseInt(textView.getText().toString());
        if (current_num > 0)
            textView.setText((current_num - 1) + "");
    }

    @Override
    public void onDestroy() {
        petTitle = buttonPet.getText().toString();
        seatTitle = buttonSeats.getText().toString();
        carTitle = buttonCar.getText().toString();
        bagsTitle = button_bags.getText().toString();
        super.onDestroy();
    }

    public boolean validateFragment() {
        if(checkCar() && checkSeats() && checkBags()&& checkPet()){
            return true;
        }
        return false;
    }


    public Boolean checkCar() {
        if (buttonCar.getText().equals(TITLE_CAR)) {
            Toast.makeText(getActivity(), "Παρακαλω καταχωρήστε το όχημα σας", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkPet() {
        if (buttonPet.getText().equals(TITLE_PET)) {
            Toast.makeText(getActivity(), "Παρακαλω καταχωρήστε αν δεχεστε κατοικίδια", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkSeats() {
        try {
            int res = Integer.parseInt(buttonSeats.getText().toString());
            if (res < 1) {
                Toast.makeText(getActivity(), "Οι θεσεις πρεπει να ειναι τουλαχιστον μια", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Υπαρχει λαθος στην καταχωρηση των θεσεων", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean checkBags() {
        try {
            int res = Integer.parseInt(button_bags.getText().toString());
            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Υπαρχει λαθος στην καταχωρηση των αποσκευων", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public String getSeats() {
        return buttonSeats.getText().toString();
    }

    public String getPets() {
        return buttonPet.getText().toString();
    }

    public String getCar() {
        return buttonCar.getText().toString();
    }

    public String getBags() {
        return button_bags.getText().toString();
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

    public void getCars(final View view, final AlertDialog dialog) {

        new FellowTravellerAPI(globalClass).getUserCars(new UserCarsCallBack() {
            @Override
            public void onSuccess(ArrayList<CarModel> carList) {
                mExampleList = new ArrayList<>();
                mExampleList = carList;
                buildRecyclerViewForCar(view, dialog);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }
}
