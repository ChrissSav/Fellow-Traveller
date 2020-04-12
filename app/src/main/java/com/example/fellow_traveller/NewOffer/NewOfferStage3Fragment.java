package com.example.fellow_traveller.NewOffer;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TextView seats_tv, bags_tv;
    private String num_of_seats = "0";
    private String num_of_bags = "0";
    private Switch pet_switch;
    private Boolean pets = false;
    private Button select_seats;

    public NewOfferStage3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage3, container, false);
        select_seats = view.findViewById(R.id.NewOfferStage3Fragment_button_seat);

        select_seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog();
            }
        });
        return view;
    }

    public void openDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.number_choose, null);
        Button button = mView.findViewById(R.id.choose_num_button);
        ImageButton increase = mView.findViewById(R.id.choose_num_imageButton_plus);
        ImageButton dicrease = mView.findViewById(R.id.choose_num_imageButton_minus);
        final TextView textView_number = mView.findViewById(R.id.choose_num_textView_number);
        TextView textView_title = mView.findViewById(R.id.choose_num_textView_title);
        if(!select_seats.getText().equals("Θέσεις ...")){
            textView_number.setText(select_seats.getText().toString());
        }

        textView_title.setText("Καθόρισε τον αριθμό των θέσεων");
        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                select_seats.setText(textView_number.getText().toString());

            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
            }
        });

        dicrease.setOnClickListener(new View.OnClickListener() {
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
        // Log.i("textInputLayout_pass_1", "onDestroy");
        //       num_of_seats = seats_tv.getText().toString();
//        num_of_bags = bags_tv.getText().toString();
//        if(pet_switch.isChecked())
//            pets =true;
//        else
//            pets = false;
        super.onDestroy();
    }

    public boolean isOk() {
        if (Integer.parseInt(seats_tv.getText().toString()) < 1) {
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
        return pet_switch.isChecked() + "";
    }
}
