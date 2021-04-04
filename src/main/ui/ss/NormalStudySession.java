package ui.ss;

import model.Card;
import model.Deck;
import model.ReviewCalendar;
import ui.QuizApp;
import ui.exceptions.EmptyStudyListException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A study session that prescribes the user review times for spaced repetition
public class NormalStudySession extends StudySession {

    // EFFECTS: instantiates a new scheduled study session with given deck
    public NormalStudySession(Deck d, JFrame parentFrame, QuizApp app) throws EmptyStudyListException {
        super(d, parentFrame, app);
        begin();
    }

    // REQUIRES: n > 0
    // MODIFIES: this
    // EFFECTS: uses shuffle sequence to randomize order of cards
    //          filters out cards that are not ready for review
    @Override
    public ArrayList<Card> generateStudyList(int n) {
        ArrayList<Integer> shuffleSequence = generateShuffleSequence(n);
        ArrayList<Card> list = new ArrayList<>(cards);

        for (int i = 0; i < n; i++) {
            list.set(shuffleSequence.get(i), cards.get(i));
        }

        Stream<Card> cardStream = list.stream();
        List<Card> filteredList = cardStream.filter(card -> new ReviewCalendar().time() > card.getReviewDate())
                .collect(Collectors.toList());

        list = new ArrayList<>(filteredList);

        return list;
    }

}
