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
}
