package com.example.fellow_traveller.Reviews;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private ArrayList<ReviewModel> reviewsList;



    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        public TextView textReview;
        public TextView name;
        public TextView date;
        public TextView rating;
        public CircleImageView userImage;


        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            textReview = itemView.findViewById(R.id.review_item_review_text);
            name = itemView.findViewById(R.id.review_item_user_name);
            date = itemView.findViewById(R.id.review_item_date);
            rating = itemView.findViewById(R.id.review_item_rating);
            userImage = itemView.findViewById(R.id.review_item_user_image);
        }
    }

    public ReviewsAdapter(ArrayList<ReviewModel> revList) {
        reviewsList = revList;

    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
            ReviewsAdapter.ReviewsViewHolder evh = new ReviewsAdapter.ReviewsViewHolder(v);
            return evh;


    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewsAdapter.ReviewsViewHolder holder, int position) {

        ReviewModel currentItem = reviewsList.get(position);

        holder.name.setText(currentItem.getUser().getFullName());
        holder.date.setText(currentItem.getDate());
        holder.textReview.setText(currentItem.getDescription());
        holder.rating.setText(String.valueOf(currentItem.getRate()));
        try {
                Picasso.get().load(currentItem.getUser().getPicture()).into(holder.userImage);
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
    }



    @Override
    public int getItemCount() {
        return reviewsList.size();
    }


}
