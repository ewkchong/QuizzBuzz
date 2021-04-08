package ui.ss;

import model.Card;
import model.Deck;
import ui.DeckMenu;
import ui.QuizApp;
import ui.exceptions.EmptyStudyListException;

import javax.swing.*;
import java.util.ArrayList;

// A study session that allows the user to review all cards regardless of schedule
public class AllStudySession extends StudySession {

    // EFFECTS: constructs a study session for reviewing all cards,
    //          regardless of schedule
    public AllStudySession(Deck d, JFrame parentFrame, QuizApp app) throws EmptyStudyListException {
        super(d, parentFrame, app);
        begin();
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

    @Override
    protected void addPanels() {
        int i = 0;
        for (Card c : studyList) {
            JPanel cardPanel = new NoScheduleCardPanel(c, this, i, studyList.size(), app);
            add(cardPanel, String.valueOf(i));
            i++;
        }
    }
}
