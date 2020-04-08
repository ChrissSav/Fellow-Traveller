package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SplashActivity;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private TextView to, from;
    private RecyclerView mRecyclerView;
    private SearchResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton filterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        from =findViewById(R.id.from_search_results);
        to = findViewById(R.id.to_search_results);
        filterButton = findViewById(R.id.filter_search_result_button);

        Intent intent = getIntent();
        String toString = intent.getExtras().getString("ToPlace");
        Intent intent2 = getIntent();
        String fromString = intent.getExtras().getString("FromPlace");
        to.setText(toString);
        from.setText(fromString);

        ArrayList<SearchResultItem> resultList = new ArrayList<>();
        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));
        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));
        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));
        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));

        mRecyclerView = findViewById(R.id.search_results_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchResultsAdapter(resultList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, SearchDetailsActivity.class);
                startActivity(mainIntent);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, FiltersActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
