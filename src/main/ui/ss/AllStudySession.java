package ui.ss;

import model.Card;

import java.util.ArrayList;

import static ui.QuizApp.header;

// A study session that allows the user to review all cards regardless of schedule
public class AllStudySession extends StudySession {

    // EFFECTS: constructs a study session for reviewing all cards,
    //          regardless of schedule
    public AllStudySession(ArrayList<Card> cards) {
        super(cards);

    }

    // EFFECTS: generates a study list using all of the decks cards,
    //          does not filter out any cards
    @Override
    protected ArrayList<Card> generateStudyList(int n) {
        ArrayList<Integer> shuffleSequence = generateShuffleSequence(n);
        ArrayList<Card> list = new ArrayList<>(cards);

        for (int i = 0; i < n; i++) {
            list.set(shuffleSequence.get(i), cards.get(i));
        }

        return list;
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty of card
    @Override
    protected void cardDifficulty(Card c) {
        System.out.println("\nDifficulty of card:");
        System.out.println("\t1) Hard");
        System.out.println("\t2) Good");
        System.out.println("\t3) Easy");

        while (true) {
            String diff = scanner.nextLine();
            if (diff.equals("2") || diff.equals("3")) {
                correctReviews++;
                break;
            } else if (diff.equals("1")) {
                break;
            } else {
                System.out.println("Invalid input, please try again!");
            }
        }
    }
}
