package ui;

import model.Card;
import model.ReviewCalendar;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ui.QuizApp.header;

// a study session that displays flash cards for user to study
public class StudySession {
    private ArrayList<Card> cards;      // list of cards from deck, un-shuffled
    private ArrayList<Card> studyList;  // list of cards from deck, shuffled
    private Scanner scanner;            // scanner for user input
    private int correctReviews;         // amount of cards marked as correct during study session

    // EFFECTS: constructs a new session with given list of cards to study
    public StudySession(ArrayList<Card> cards) {
        this.cards = cards;
        studyList = generateStudyList(cards.size());
        this.scanner = new Scanner(System.in);
        beginStudySession();

    }

    // REQUIRES: n > 0
    // MODIFIES: this
    // EFFECTS: uses shuffle sequence to randomize order of cards
    //          filters out cards that are not ready for review
    private ArrayList<Card> generateStudyList(Integer n) {
        ArrayList<Integer> shuffleSequence = generateShuffleSequence(n);
        ArrayList<Card> list = new ArrayList<>(cards);

        for (int i = 0; i < n; i++) {
            list.set(shuffleSequence.get(i), cards.get(i));
        }

        Stream<Card> cardStream = list.stream();
        System.out.println(new ReviewCalendar().displayTime());
        List<Card> filteredList = cardStream.filter(card -> new ReviewCalendar().time() > card.getReviewDate())
                                            .collect(Collectors.toList());

        list = new ArrayList<>(filteredList);

        return list;
    }

    // REQUIRES: n > 0
    // EFFECTS: generates a random sequence of integers from 0 to n
    private static ArrayList<Integer> generateShuffleSequence(Integer n) {
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
    private void beginStudySession() {
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
    private String calcSuccessRate() {
        double correct = correctReviews;
        double total = cards.size();
        double rate = (correct / total) * 100;

        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(rate);
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty of card
    private void cardDifficulty(Card c) {
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
