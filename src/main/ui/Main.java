package ui;

import persistence.Reader;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader("./data/decks.json");
        new QuizApp(reader.read());
    }
}
