package com.example.fellow_traveller.Chat;

public class MessageItem {
    private int id;
    private String text;
    private String name;

    public MessageItem(int aId, String aText, String aName){
        id = aId;
        text = aText;
        name = aName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
