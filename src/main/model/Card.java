package model;

import org.json.JSONObject;
import java.util.ArrayList;

// A flash card with tags
public class Card {
    private String front;            // Content on the "front" of the card
    private String back;             // Content on the "back" of the card
    private ArrayList<String> tags;  // List of tags applied to card
    private long lastReviewDate;
    private long reviewDate;
    private double ease;
    private int status;

    // Constructs a card
    // EFFECTS: Card created with front, back, and tags
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

    // MODIFIES: this
    // EFFECTS: changes front text of card to given string
    public void changeFront(String s) {
        this.front = s;
    }

    // MODIFIES: this
    // EFFECTS: changes back text of card to given string
    public void changeBack(String s) {
        this.back = s;
    }

    // MODIFIES: this
    // EFFECTS: removes tag named entry if in list,
    //          adds tag if tag entry not already in list
    public void changeTag(int i, String entry) {
        if (i != -1) {
            tags.remove(i);
        } else {
            tags.add(entry);
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("front", front);
        json.put("back", back);
        json.put("tags", tags);
        return json;
    }
}
