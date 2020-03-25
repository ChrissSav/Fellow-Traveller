package com.example.fellow_traveller.Register_Feature;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage1Fragment extends Fragment {


    public RegisterStage1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_stage_1, container, false);
    }

    public String toString(){
        return "RegisterStage1Fragment";
    }

    public int getRank(){
        return 1;
    }

}
