package model;

import java.util.ArrayList;

public class Card {
    private String front;
    private String back;
    private ArrayList<String> tags;

    public Card(String front, String back, ArrayList<String> tags) {
        this.front = front;
        this.back = back;
        this.tags = tags;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void viewCard() {
        System.out.println("Front: " + this.front);
        System.out.println("Back: " + this.back);
        System.out.println("Tags: " + this.tags.toString());
    }

    public void changeFront(String s) {
        this.front = s;
    }

    public void changeBack(String s) {
        this.back = s;
    }
}
