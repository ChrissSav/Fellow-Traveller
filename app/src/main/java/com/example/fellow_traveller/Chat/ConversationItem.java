package com.example.fellow_traveller.Chat;

public class ConversationItem implements Comparable{
    private int tripId;
    private String tripName ;
    private String description;
    private long date;
    private boolean seen;

    public ConversationItem(int aId, String aUserName, String aDescription, long aDate, boolean aSeen){

        tripId = aId;
        tripName = aUserName;
        description = aDescription;
        date = aDate;
        seen = aSeen;
    }


    public ConversationItem(){}

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public int compareTo(Object o) {
            if(this.getDate()>((ConversationItem) o).getDate()) {
                return -1;
            }else{
                return 1;
            }
    }


}
