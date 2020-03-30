package com.example.fellow_traveller.HomeFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fellow_traveller.Chat.ChatConversationActivity;
import com.example.fellow_traveller.Chat.ConversationAdapter;
import com.example.fellow_traveller.Chat.ConversationItem;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessengerFragment extends Fragment {
    private Button btn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public MessengerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messenger, container, false);
        btn = view.findViewById(R.id.button);


        ArrayList<ConversationItem> conversationsList = new ArrayList<>();
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Martin Garrix", "Yes i finished the last one", "20:27", true));
        conversationsList.add(new ConversationItem("Josh Dun", "Is Tyler over there?", "20:11", true));
        conversationsList.add(new ConversationItem("Tyler Joseph", "Heyy!!Im coming to Filotas", "19:15", true));
        conversationsList.add(new ConversationItem("Ioanna Drivakou", "Καλάα και εγώ τώρα τέλειωσα", "18:52", true));
        conversationsList.add(new ConversationItem("Kashmir", "That's lit!!!", "17:27", true));
        conversationsList.add(new ConversationItem("Mum", "Βγες και λίγο από το δωμάτιο", "17:11", true));

        mRecyclerView = view.findViewById(R.id.conversations_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mAdapter = new ConversationAdapter(conversationsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ChatConversationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
