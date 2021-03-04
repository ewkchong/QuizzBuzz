package model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

// A flash card with tags
public class Card {
    private String front;            // Content on the "front" of the card
    private String back;             // Content on the "back" of the card
    private ArrayList<String> tags;  // List of tags applied to card
    private int reviewCount;
    private long currentInterval;    // Current review interval in hours
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
        reviewCount = 0;
        status = 0;
        lastReviewDate = 0;
        reviewDate = 0;
        ease = 2.5;
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

    public double getEase() {
        return ease;
    }

    public long getCurrentInterval() {
        return currentInterval;
    }

    public long getReviewDate() {
        return reviewDate;
    }

    public void setFront(String s) {
        this.front = s;
    }

    public void setBack(String s) {
        this.back = s;
    }

    public void setCurrentInterval(int i) {
        this.currentInterval = i;
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

    // !!!
    // EFFECTS: returns a JSONObject representation of this object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("front", front);
        json.put("back", back);
        json.put("tags", tags);
        return json;
    }

    // !!!
    // MODIFIES: this
    // EFFECTS: determines the amount of days before next review date
    public void processReview(int diff) {
        calculateEase(diff);
        if (reviewCount == 0) {
            currentInterval = 24;
        } else if (reviewCount == 1) {
            currentInterval = 24 * 6;
        } else {
            currentInterval *= ease;
        }
        if (currentInterval >= (24 * 21)) {
            status = 1;
        }

        long day = new ReviewCalendar().time();
        lastReviewDate = day;
        reviewDate = day + currentInterval;
    }

    // !!!
    private void calculateEase(int diff) {
        if (ease <= 1.2) {
            ease = 1.2;
        } else {
            ease += (0.1 - (3 - diff) * (0.08 + (3 - diff) * 0.02));
        }
    }
}
