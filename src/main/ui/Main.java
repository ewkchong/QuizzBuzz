package ui;

import org.json.JSONException;
import persistence.Reader;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader("./data/decks.json");
        try {
            new QuizApp(reader.read());
        } catch (IOException | JSONException e) {
            new QuizApp(new ArrayList<>());
        }
    }
}
