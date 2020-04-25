package com.example.fellow_traveller.Reviews;

public class ReviewItem {
    private String image;
    private String userName;
    private String date;
    private String rating;
    private String reviewText;

    public ReviewItem(String aUserName, String aDate, String aRating, String aReviewText) {

        this.userName = aUserName;
        this.date = aDate;
        this.rating = aRating;
        this.reviewText = aReviewText;
    }

    public ReviewItem() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
