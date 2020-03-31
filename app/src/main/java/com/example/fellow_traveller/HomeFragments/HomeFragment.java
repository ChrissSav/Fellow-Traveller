package com.example.fellow_traveller.HomeFragments;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fellow_traveller.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView searchForaTrip;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayout2;
    private View view;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home,container,false);
        searchForaTrip = view.findViewById(R.id.FragmentHome_textView_Search);
        constraintLayout= view.findViewById(R.id.Layout2);
        constraintLayout2=view.findViewById(R.id.Layout3);

        searchForaTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int visibility=constraintLayout.getVisibility();
                if(visibility==View.VISIBLE) {
                    constraintLayout.setVisibility(View.GONE);
                    constraintLayout2.setVisibility(View.GONE);
                }
                else {
                    constraintLayout.setVisibility(View.VISIBLE);
                    constraintLayout2.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}