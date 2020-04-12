package com.example.fellow_traveller.Chat;

public class MessageItem {
    private int id;
    private String text;
    private int groupId;
    private long timestamp;

    public MessageItem(int aId, String aText, String aName, int aGroupId, long aTimestamp){
        id = aId;
        text = aText;
        groupId = aGroupId;
        timestamp = aTimestamp;
    }
    public MessageItem(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
