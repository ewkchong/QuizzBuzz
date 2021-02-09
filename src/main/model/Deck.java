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

    public void printCards() {
        if (cardList.size() == 0) {
            System.out.println("No cards in current deck");
        } else {
            int i = 1;
            for (Card c : cardList) {
                System.out.println("Card " + i);
                i++;
            }
        }
    }
}
