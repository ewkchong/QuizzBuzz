package ui;

import model.Deck;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// A flash card application
public class QuizApp {
    private ArrayList<Deck> decks;     // List of decks created by user
    private JFrame frame;

    // EFFECTS: instantiates new application for user interface
    public QuizApp(ArrayList<Deck> decks) {
        this.decks = decks;
        initializeFrame();
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public JFrame getFrame() {
        return frame;
    }

    // EFFECTS: changes list of decks into a JSON array
    public JSONArray deckListToJson() {
        JSONArray array = new JSONArray();
        for (Deck d : decks) {
            array.put(d.toJson());
        }
        return array;
    }

    // MODIFIES this
    // EFFECTS: creates and shows UI
    public void initializeFrame() {
        frame = new JFrame("QuizzBuzz");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("data/images/logo.png")));
            frame.setIconImage(icon.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(new MainMenu(this));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: changes the frame to a deck menu of given deck
    public void returnToDeckMenu(Deck d) {
        JFrame frame = this.getFrame();
        frame.setContentPane(new DeckMenu(this, d));
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds (Unsaved Changes) to the end of title
    public void updateTitle() {
        frame.setTitle("QuizzBuzz (Unsaved Changes)");
    }

}
