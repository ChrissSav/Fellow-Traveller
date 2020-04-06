package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOfferStage3Fragment extends Fragment {


    private View view;
    private ImageButton seats_increase, seats_decrease, bags_increase, bags_decrease;
    private TextView seats_tv,bags_tv;
    private String num_of_seats = "0";
    private String num_of_bags = "0";
    private Switch pet_switch;
    private Boolean pets = false;
    public NewOfferStage3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage3, container, false);
        seats_increase = view.findViewById(R.id.NewOfferStage3Fragment_button_plus_seats);
        seats_decrease = view.findViewById(R.id.NewOfferStage3Fragment_button_minus_seats);

        bags_increase = view.findViewById(R.id.NewOfferStage3Fragment_button_plus_bags);
        bags_decrease = view.findViewById(R.id.NewOfferStage3Fragment_button_minus_bags);

        seats_tv = view.findViewById(R.id.NewOfferStage3Fragment_TextView_seats);
        bags_tv = view.findViewById(R.id.NewOfferStage3Fragment_TextView_bags);

        pet_switch = view.findViewById(R.id.NewOfferStage3Fragment_switch_pets);
        seats_tv.setText(num_of_seats);
        bags_tv.setText(num_of_bags);

        if(pets){
            pet_switch.setChecked(true);
            pet_switch.setText("Allow");
        }

        seats_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(seats_tv);
            }
        });

        seats_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(seats_tv);
            }
        });

        bags_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(bags_tv);
            }
        });

        bags_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(bags_tv);
            }
        });

        bags_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(bags_tv);
            }
        });



        pet_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pet_switch.setText("Allow");
                }else{
                    pet_switch.setText("Not Allow");
                }
            }
        });

        return view;
    }

    public String toString() {
        return "NewOfferStage3Fragment";
    }

    public int getRank() {
        return 3;
    }

    public void Increase(TextView textView){
        int current_num = Integer.parseInt(textView.getText().toString());
        textView.setText((current_num+1)+"");

    }
    public void Decrease(TextView textView){
        int current_num = Integer.parseInt(textView.getText().toString());
        if(current_num > 0)
            textView.setText((current_num-1)+"");
    }

    @Override
    public void onDestroy() {
        // Log.i("textInputLayout_pass_1", "onDestroy");
        num_of_seats = seats_tv.getText().toString();
        num_of_bags = bags_tv.getText().toString();
        if(pet_switch.isChecked())
            pets =true;
        else
            pets = false;
        super.onDestroy();
    }
    public boolean isOk(){
        if(Integer.parseInt(seats_tv.getText().toString())<1){
            Toast.makeText(getActivity(), "Ο αριθμός των θέσων πρέπει να ειναι τουλάστον 1!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public String getNum_of_seats() {
        return seats_tv.getText().toString();
    }
    public String getNum_of_bags() {
        return bags_tv.getText().toString();
    }
    public String getPet() {
        return pet_switch.isChecked()+"";
    }
}
