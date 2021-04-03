package ui.ss;

import model.Card;
import ui.DeckMenu;
import ui.QuizApp;
import ui.exceptions.EmptyStudyListException;

import javax.swing.*;
import java.util.ArrayList;

// A study session that allows the user to review all cards regardless of schedule
public class AllStudySession extends StudySession {

    // EFFECTS: constructs a study session for reviewing all cards,
    //          regardless of schedule
    public AllStudySession(ArrayList<Card> cards, JFrame parentFrame, QuizApp app) throws EmptyStudyListException {
        super(cards, parentFrame, app);
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
}
