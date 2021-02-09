package model;

import java.util.ArrayList;

public class Deck {
    private String title;
    private ArrayList<Card> cardList;

    public Deck(String title) {
        this.title = title;
        cardList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }
}
