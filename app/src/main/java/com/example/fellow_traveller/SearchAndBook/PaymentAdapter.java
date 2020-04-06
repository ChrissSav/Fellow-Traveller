package com.example.fellow_traveller.SearchAndBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fellow_traveller.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PaymentAdapter extends ArrayAdapter<PaymentItem> {

    public PaymentAdapter(Context context, ArrayList<PaymentItem> aMethodList){
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
            converView = LayoutInflater.from(getContext()).inflate(R.layout.payment_book_spinner_item, parent, false);
        }

        ImageView imageMethod = converView.findViewById(R.id.payment_item_image);
        TextView textMethod = converView.findViewById(R.id.payment_item_tv);

        PaymentItem curr = getItem(position);

        if(curr != null) {
            imageMethod.setImageResource(curr.getSelectMethod());
            textMethod.setText(curr.getPaymentMethod());
        }
        return converView;
    }
}
