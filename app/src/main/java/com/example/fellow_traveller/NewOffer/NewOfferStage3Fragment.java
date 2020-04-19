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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.Adapters.CarAdapter;
import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Settings.AddCarSettingsActivity;

import java.util.ArrayList;


public class NewOfferStage3Fragment extends Fragment {
    private final String CLICK_COLOR = "#1C1C1C";
    private final String TITLE_PET = "Επέλεξε ...";
    private final String TITLE_SEAT = "Θέσεις ...";
    private final String TITLE_CAR = "Διάλεξε αυτοκίνητο ...";
    private final String TITLE_BAGS = "Αποσκεύες ...";

    private View view;
    private Button button_seats, button_pet, button_car, button_bags;
    //Backup
    private String pet_title = TITLE_PET;
    private String seat_title = TITLE_SEAT;
    private String car_title = TITLE_CAR;
    private String bags_title = TITLE_BAGS;

    //Recycle
    private RecyclerView mRecyclerView;
    private CarAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Car> mExampleList;

    public NewOfferStage3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage3, container, false);

        button_seats = view.findViewById(R.id.NewOfferStage3Fragment_button_seat);
        button_pet = view.findViewById(R.id.NewOfferStage3Fragment_button_pet);
        button_car = view.findViewById(R.id.NewOfferStage3Fragment_button_car);
        button_bags = view.findViewById(R.id.NewOfferStage3Fragment_button_bags);


        button_pet.setText(pet_title);
        button_seats.setText(seat_title);
        button_car.setText(car_title);
        button_bags.setText(bags_title);


        //Text Color
        if (button_pet.getText() != TITLE_PET) {
            button_pet.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (button_bags.getText() != TITLE_BAGS) {
            button_bags.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (button_car.getText() != TITLE_CAR) {
            button_car.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (button_seats.getText() != TITLE_SEAT) {
            button_seats.setTextColor(Color.parseColor(CLICK_COLOR));
        }


        button_seats.setOnClickListener(new View.OnClickListener() {
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


        button_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogForCar();
            }
        });

        button_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_pet.setTextColor(Color.parseColor(CLICK_COLOR));
                if (button_pet.getText().equals(TITLE_PET) || button_pet.getText().equals("Δεν επιτρέπω")) {
                    button_pet.setText("Επιτρέπω");
                    return;
                }
                if (button_pet.getText().equals("Επιτρέπω")) {
                    button_pet.setText("Δεν επιτρέπω");
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

        FillList(mView, dialog);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Intent intent = new Intent(getActivity(), AddCarSettingsActivity.class);
                startActivity(intent);

            }
        });


    }

    public void buildRecyclerView(View view, final AlertDialog dialog) {
        mRecyclerView = view.findViewById(R.id.choose_car_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CarAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                button_car.setTextColor(Color.parseColor(CLICK_COLOR));
                button_car.setText(mExampleList.get(position).getDescription());
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
        if (!button_seats.getText().equals(TITLE_SEAT)) {
            textView_number.setText(button_seats.getText().toString());
        }

        textView_title.setText("Καθόρισε τον αριθμό των θέσεων");
        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                button_seats.setTextColor(Color.parseColor(CLICK_COLOR));
                button_seats.setText(textView_number.getText().toString());

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
            textView_number.setText(button_seats.getText().toString());
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
        pet_title = button_pet.getText().toString();
        seat_title = button_seats.getText().toString();
        car_title = button_car.getText().toString();
        bags_title = button_bags.getText().toString();
        super.onDestroy();
    }

    public boolean isOk() {
        if(CheckCar() && CheckSeats() && CheckBags()&& CheckPet()){
            return true;
        }
        return false;
    }


    public Boolean CheckCar() {
        if (button_car.getText().equals(TITLE_CAR)) {
            Toast.makeText(getActivity(), "Παρακαλω καταχωρήστε το όχημα σας", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Boolean CheckPet() {
        if (button_pet.getText().equals(TITLE_PET)) {
            Toast.makeText(getActivity(), "Παρακαλω καταχωρήστε αν δεχεστε ζωα", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Boolean CheckSeats() {
        try {
            int res = Integer.parseInt(button_seats.getText().toString());
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

    public Boolean CheckBags() {
        try {
            int res = Integer.parseInt(button_bags.getText().toString());
            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Υπαρχει λαθος στην καταχωρηση των αποσκευων", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void FillList(View view, AlertDialog dialog) {
        mExampleList = new ArrayList<>();
        Car car = new Car("Nissan", "Navara", "ZXB1025");
        mExampleList.add(car);
        car = new Car("Toyota", "Hilux", "ZXB1415");
        mExampleList.add(car);
        buildRecyclerView(view, dialog);

    }
}
