package model;

import java.util.ArrayList;

// A deck containing flash cards
public class Deck {
    private String title;                              // title of deck
    private ArrayList<Card> cardList;                  // all cards in deck

    // REQUIRES: title given must be of non-zero length
    // EFFECTS: instantiates new deck with given title and empty list of cards
    public Deck(String title) {
        this.title = title;
        cardList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    // MODIFIES: this
    // EFFECTS: adds a new card to given deck with user-input generated properties
    public void addCard(String front, String back, ArrayList<String> tags) {
        Card c = new Card(front, back, tags);
        cardList.add(c);

    }

    // REQUIRES: e must be between zero and the size of the card list
    // MODIFIES: this
    // EFFECTS: removes selected card from deck
    public void deleteCard(int e) {
        if (!(e == 0)) {
            cardList.remove(e - 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the title of the deck to user-input string
    public void renameDeck(String t) {
        this.title = t;
    }
}
