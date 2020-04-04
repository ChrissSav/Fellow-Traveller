package com.example.fellow_traveller.PlacesAPI;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class PlaceAutocompleteAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> results;
    int resource;
    Context context;
    PlacesAPI placesAPI = new PlacesAPI();


    public PlaceAutocompleteAdapter(Context context,int resId){
        super(context,resId);
        this.context = context;
        this.resource = resId;

    }

    @Override
    public int getCount(){
        return results.size();
    }

    @Override
    public String getItem(int pos){
        return results.get(pos);
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence!=null){
                    results = placesAPI.autocomplete(charSequence.toString());

                    filterResults.values = results;
                    filterResults.count = results.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(results!=null && results.size()>0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }
        };
    return filter;
    }
}
