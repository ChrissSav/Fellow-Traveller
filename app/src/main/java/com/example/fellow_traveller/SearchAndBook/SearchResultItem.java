package com.example.fellow_traveller.SearchAndBook;

public class SearchResultItem {
    private String userName;
    private String rate;
    private String review;
    private String from;
    private String to;
    private String date;
    private String time;

    public SearchResultItem(String aUserName, String aRate, String aReview, String aFrom, String aTo, String aDate, String aTime){
        this.userName = aUserName;
        this.rate = aRate;
        this.review = aReview;
        this.from = aFrom;
        this.to = aTo;
        this.date = aDate;
        this.time = aTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
