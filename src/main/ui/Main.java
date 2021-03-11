package ui;

import org.json.JSONException;
import persistence.JsonReader;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonReader reader = new JsonReader("./data/decks.json");
        try {
            new QuizApp(reader.read());
        } catch (IOException | JSONException e) {
            new QuizApp(new ArrayList<>());
        }
    }
}
