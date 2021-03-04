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

    /*
     * EFFECTS: displays front of each card in study list,
     *          one at a time, displays back of card
     *          when ENTER is pressed by user
     */
    @Override
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
                break;
            } else if (diff.equals("1")) {
                break;
            } else {
                System.out.println("Invalid input, please try again!");
            }
        }
    }
}
