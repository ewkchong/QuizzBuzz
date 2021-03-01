package persistence;

import model.Deck;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Reader {
    private String path;

    public Reader(String path) {
        this.path = path;
    }

    public String getStringFromJson() {
        String s = "";
        try {
            s = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("File cannot be found!");
        }
        return s;
    }

    public ArrayList<Deck> read() {
        String jsonString = getStringFromJson();
        JSONArray json = new JSONArray(jsonString);
        return parseDeckList(json);
    }

    public ArrayList<Deck> parseDeckList(JSONArray json) {
        return null;
    }

    public void parseDeck() {

    }

    public void parseCardList() {

    }

    public void parseCard() {

    }
}
