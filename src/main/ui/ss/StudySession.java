package ui.ss;

import model.Card;
import model.Deck;
import ui.QuizApp;
import ui.exceptions.EmptyStudyListException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

// a study session that displays flash cards for user to study
public abstract class StudySession extends JPanel {
    protected QuizApp app;                // parent application
    protected Deck deck;                  // deck for this study session
    protected ArrayList<Card> cards;      // list of cards from deck, un-shuffled
    protected ArrayList<Card> studyList;  // list of cards from deck, shuffled
    protected int correctReviews;         // amount of cards marked as correct during study session
    protected JFrame parentFrame;         // containing frame

    // EFFECTS: constructs a new session with given list of cards to study
    public StudySession(Deck deck,
                        JFrame frame,
                        QuizApp app) {
        this.app = app;
        this.parentFrame = frame;
        this.deck = deck;
        this.cards = deck.getCardList();
        setLayout(new CardLayout());
    }

    public Deck getDeck() {
        return deck;
    }

    // EFFECTS: selects the proper cards to add to study session
    protected abstract ArrayList<Card> generateStudyList(int n);

    // REQUIRES: n > 0
    // EFFECTS: generates a random sequence of integers from 0 to n
    protected ArrayList<Integer> generateShuffleSequence(Integer n) {
        ArrayList<Integer> sequence = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            sequence.add(i);
        }
        Collections.shuffle(sequence);
        return sequence;
    }

    // MODIFIES: this
    /*
     * EFFECTS: displays front of each card in study list,
     *          one at a time, displays back of card.
     *          throws EmptyStudyListException if
     *          filtered list has no cards
     */
    public void begin() throws EmptyStudyListException {
        studyList = generateStudyList(cards.size());
        if (studyList.size() == 0) {
            throw new EmptyStudyListException();
        } else {
            int i = 0;
            for (Card c : studyList) {
                JPanel cardPanel = new CardPanel(c, this, i, studyList.size(), app);
                add(cardPanel, String.valueOf(i));
                i++;
            }
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, String.valueOf(0));
        }
    }

    // EFFECTS: calculates proportion of correct cards reviewed
    private String calcSuccessRate() {
        double correct = correctReviews;
        double total = studyList.size();
        double rate = (correct / total) * 100;

        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(rate);
    }

    // MODIFIES: this
    // EFFECTS: increments the amount of correct reviews by 1
    protected void incrementCorrectReviews() {
        correctReviews++;
    }

    // EFFECTS: displays a dialog showing percentage of correct reviews
    protected void showPerformanceDialog() {
        String successRate = calcSuccessRate();
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(new File("data/images/partypopper.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(parentFrame,
                "You answered " + successRate + "% of " + studyList.size() + " cards correctly!",
                "Performance",
                JOptionPane.PLAIN_MESSAGE,
                icon);
    }
}
