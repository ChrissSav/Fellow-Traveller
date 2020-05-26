package com.example.fellow_traveller.Register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.PaymentItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



    public class CountryCodeAdapter extends ArrayAdapter<CountryCodeItem> {

        public CountryCodeAdapter(Context context, ArrayList<CountryCodeItem> aMethodList){
            super(context,0,aMethodList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        public View initView(int position, View converView, ViewGroup parent){
            if(converView==null){
                converView = LayoutInflater.from(getContext()).inflate(R.layout.country_code_spinner_item, parent, false);
            }

            ImageView imageMethod = converView.findViewById(R.id.country_code_item_image);
            TextView textMethod = converView.findViewById(R.id.country_code_item_textView);

            CountryCodeItem curr = getItem(position);

            if(curr != null) {
                imageMethod.setImageResource(curr.getCountryImage());
                textMethod.setText(curr.getCodeNumber());
            }
            return converView;
        }
    }


