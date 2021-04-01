package ui;

import model.Card;
import model.Deck;
import org.json.JSONArray;
import persistence.JsonWriter;
import ui.ss.AllStudySession;
import ui.ss.NormalStudySession;
import ui.ss.StudySession;
import ui.ss.TagStudySession;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Scanner;

// A flash card application
public class QuizApp {
    private ArrayList<Deck> decks;     // List of decks created by user
    private final Scanner scanner;     // Scanner for user input

    // EFFECTS: instantiates new application for user interface
    public QuizApp(ArrayList<Deck> decks) {
        scanner = new Scanner(System.in);
        this.decks = decks;
        initializeFrame();
    }

    // EFFECTS: creates new QuizApp instance with given list of decks
    //          and given scanner
    public QuizApp(ArrayList<Deck> decks, Scanner scanner) {
        this.scanner = scanner;
        this.decks = decks;
    }

    // EFFECTS: creates new QuizApp instance with empty list of decks
    //          and default scanner
    public QuizApp() {
        scanner = new Scanner(System.in);
        decks = new ArrayList<>();
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    // EFFECTS: changes list of decks into a JSON array
    public JSONArray deckListToJson() {
        JSONArray array = new JSONArray();
        for (Deck d: decks) {
            array.put(d.toJson());
        }
        return array;
    }

    // EFFECTS: creates and shows UI
    public void initializeFrame() {
        JFrame frame = new JFrame("QuizzBuzz");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("data/images/logo.png")));
            frame.setIconImage(icon.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(new MainMenu(decks, frame, this));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
