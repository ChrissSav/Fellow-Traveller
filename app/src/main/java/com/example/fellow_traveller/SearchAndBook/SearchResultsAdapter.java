package com.example.fellow_traveller.SearchAndBook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {
    private ArrayList<TripModel> searchResultList;
    private OnItemClickListener mListener;

    public SearchResultsAdapter(ArrayList<TripModel> searchList) {
        searchResultList = searchList;

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        SearchResultsViewHolder evh = new SearchResultsViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position) {
        TripModel currentItem = searchResultList.get(position);

        holder.userName.setText(currentItem.getCreator().getFullName());
        // TODO cast this to double
        holder.rate.setText(String.valueOf(currentItem.getCreator().getRate()));
        holder.review.setText(String.valueOf(currentItem.getCreator().getReviews()));
        holder.from.setText(String.valueOf(currentItem.getDestFrom()));
        holder.to.setText(String.valueOf(currentItem.getDestTo()));
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, rate, review, from, to, date, time;


        public SearchResultsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.name_search_item);
            rate = itemView.findViewById(R.id.rate_search_item);
            review = itemView.findViewById(R.id.review_search_item);
            from = itemView.findViewById(R.id.from_search_item);
            to = itemView.findViewById(R.id.to_search_item);
            date = itemView.findViewById(R.id.date_search_item);
            time = itemView.findViewById(R.id.time_search_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
