package ui.ss;

import model.Card;
import model.ReviewCalendar;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ui.QuizApp.header;

// a study session that displays flash cards for user to study
public abstract class StudySession {
    protected ArrayList<Card> cards;      // list of cards from deck, un-shuffled
    protected ArrayList<Card> studyList;  // list of cards from deck, shuffled
    protected Scanner scanner;            // scanner for user input
    protected int correctReviews;         // amount of cards marked as correct during study session

    // EFFECTS: constructs a new session with given list of cards to study
    public StudySession(ArrayList<Card> cards) {
        this.cards = cards;
        this.scanner = new Scanner(System.in);
    }

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

    /*
     * EFFECTS: displays front of each card in study list,
     *          one at a time, displays back of card
     *          when ENTER is pressed by user
     */
    public void begin() {
        studyList = generateStudyList(cards.size());
        header("Study Session");
        if (studyList.size() != 0) {
            int i = 1;
            for (Card c : studyList) {
                header("Card " + i);
                System.out.println("\tFront: " + c.getFront());
                while (true) {
                    System.out.println("\nPress ENTER to show answer...");
                    if (scanner.nextLine().equals("")) {
                        System.out.println("\tBack: " + c.getBack());
                        break;
                    }
                }
                cardDifficulty(c);
                i++;
            }

            System.out.println("Study session complete!");
            System.out.println("Success Rate: " + calcSuccessRate() + "% of " + cards.size() + " cards reviewed");
        } else {
            System.out.println("No cards to study!");
        }
    }

    // REQUIRES: cards.size() > 0
    // EFFECTS: calculates proportion of correct cards reviewed
    protected String calcSuccessRate() {
        double correct = correctReviews;
        double total = studyList.size();
        double rate = (correct / total) * 100;

        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(rate);
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty of card
    protected void cardDifficulty(Card c) {
        System.out.println("\nDifficulty of card:");
        System.out.println("\t1) Hard");
        System.out.println("\t2) Good");
        System.out.println("\t3) Easy");

        while (true) {
            String diff = scanner.nextLine();
            if (diff.equals("2") || diff.equals("3")) {
                correctReviews++;
                c.processReview(Integer.parseInt(diff));
                break;
            } else if (diff.equals("1")) {
                c.processReview(Integer.parseInt(diff));
                break;
            } else {
                System.out.println("Invalid input, please try again!");
            }
        }
    }
}
