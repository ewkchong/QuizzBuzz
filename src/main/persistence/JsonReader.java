package persistence;

import model.Card;
import model.Deck;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonReader {
    private String path;

    public JsonReader(String path) {
        this.path = path;
    }

    public String getStringFromJson() throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

    public ArrayList<Deck> read() throws IOException {
        String jsonString = getStringFromJson();
        JSONArray json = new JSONArray(jsonString);
        return parseDeckList(json);
    }

    public ArrayList<Deck> parseDeckList(JSONArray json) {
        ArrayList<Deck> deckList = new ArrayList<>();
        for (Object deck : json) {
            JSONObject jsonObject = (JSONObject) deck;
            deckList.add(parseDeck((JSONObject) deck));
        }
        return deckList;
    }

    public Deck parseDeck(JSONObject deck) {
        String title = deck.getString("title");

        return new Deck(title, parseCardList(deck));
    }

    public ArrayList<Card> parseCardList(JSONObject deck) {
        ArrayList<Card> cardList = new ArrayList<>();
        JSONArray cardArray = deck.getJSONArray("cardList");
        for (Object card : cardArray) {
            cardList.add(parseCard((JSONObject) card));
        }
        return cardList;
    }

    public Card parseCard(JSONObject card) {
        String front = card.getString("front");
        String back = card.getString("back");
        ArrayList<String> tags = parseTags(card);

        Card c = new Card(front, back, tags).setReviewCount(card.getInt("reviewCount"))
                .setCurrentInterval(card.getLong("currentInterval"))
                .setReviewDate(card.getLong("reviewDate"))
                .setEase(card.getDouble("ease"))
                .setStatus(card.getInt("status"));

        return c;
    }

    private ArrayList<String> parseTags(JSONObject card) {
        ArrayList<String> tags = new ArrayList<>();
        JSONArray tagArray = card.getJSONArray("tags");
        for (Object tag: tagArray) {
            tags.add((String) tag);
        }
        return tags;
    }
}
