package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fellow_traveller.Chat.ConversationAdapter;
import com.example.fellow_traveller.Chat.ConversationItem;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private TextView to, from;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        from =findViewById(R.id.from_search_results);
        to = findViewById(R.id.to_search_results);
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
    }
}
