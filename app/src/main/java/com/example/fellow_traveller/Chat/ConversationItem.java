package com.example.fellow_traveller.Chat;

public class ConversationItem {
    private String userName;
    private String description;
    private String date;
    private boolean seen;

    public ConversationItem(String aUserName, String aDescription, String aDate, boolean aSeen){

        userName = aUserName;
        description = aDescription;
        date = aDate;
        seen = aSeen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
