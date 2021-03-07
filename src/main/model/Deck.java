package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// A deck containing flash cards
public class Deck {
    private String title;                              // title of deck
    private final ArrayList<Card> cardList;                  // all cards in deck

    // EFFECTS: instantiates new deck with given title and empty list of cards
    public Deck(String title) {
        this.title = title;
        cardList = new ArrayList<>();
    }

    // EFFECTS: instantiates new deck with given title and given list of cards
    public Deck(String title, ArrayList<Card> cardList) {
        this.title = title;
        this.cardList = cardList;
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

    // MODIFIES: this
    // EFFECTS: adds a card c to given deck
    public void addCard(Card c) {
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

    // EFFECTS: returns a json representation of the deck object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("cardList", cardListToJson());
        return json;
    }

    // EFFECTS: returns a json array representation of the card list
    public JSONArray cardListToJson() {
        JSONArray array = new JSONArray();
        for (Card c : cardList) {
            array.put(c.toJson());
        }
        return array;
    }
}
