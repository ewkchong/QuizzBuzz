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

    // REQUIRES: s is a non-empty string
    // MODIFIES: this
    // EFFECTS: changes front text of card to given string
    public void changeFront(String s) {
        this.front = s;
    }

    // REQUIRES: s is a non-empty string
    // MODIFIES: this
    // EFFECTS: changes back text of card to given string
    public void changeBack(String s) {
        this.back = s;
    }
}
