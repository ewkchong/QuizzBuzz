package ui.ss;

import model.Card;

import java.util.ArrayList;

import static ui.QuizApp.header;

public class AllStudySession extends StudySession {

    public AllStudySession(ArrayList<Card> cards) {
        super(cards);

    }

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
