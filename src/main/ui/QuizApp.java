package ui;

import model.Card;
import model.Deck;
import model.exceptions.EmptyStudyListException;
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
    private final ArrayList<Deck> decks;     // List of decks created by user
    private final Scanner scanner;           // Scanner for user input

    // EFFECTS: instantiates new application for user interface
    public QuizApp(ArrayList<Deck> decks) {
        scanner = new Scanner(System.in);
        this.decks = decks;
        initializeFrame();
    }

    public QuizApp(ArrayList<Deck> decks, Scanner scanner) {
        this.scanner = scanner;
        this.decks = decks;
    }

    public QuizApp() {
        scanner = new Scanner(System.in);
        decks = new ArrayList<>();
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    // EFFECTS: changes list of decks into a JSON array
    public JSONArray deckListToJson() {
        JSONArray array = new JSONArray();
        for (Deck d: decks) {
            array.put(d.toJson());
        }
        return array;
    }

    // EFFECTS: displays menu asking user if they would like to save changes,
    //          processes user input
    public void saveMenu() {
        System.out.println("Would you like to save all changes to decks/cards?");
        System.out.println("\t1) Yes");
        System.out.println("\t2) No");
        scanner.nextLine();
        String entry = scanner.nextLine();
        if (entry.equals("1")) {
            try {
                JsonWriter writer = new JsonWriter("./data/decks.json");
                writer.save(deckListToJson());
                System.out.println("Saved!");
            } catch (FileNotFoundException e) {
                System.out.println("File cannot be found!");
            }
        }
    }

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
