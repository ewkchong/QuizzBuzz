package ui.ss;

import model.Card;
import model.Deck;
import ui.QuizApp;
import ui.exceptions.EmptyStudyListException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A study session that allows the user to study only the specified tags
public class TagStudySession extends StudySession {
    ArrayList<String> tags;     // list of tags to study

    // constructs a new tag study session with given cards and set of tags
    public TagStudySession(Deck d, ArrayList<String> tags, QuizApp app) throws EmptyStudyListException {
        super(d, app.getFrame(), app);
        this.tags = tags;
        begin();
    }

    // MODIFIES: this
    // EFFECTS: uses shuffle sequence to randomize order of cards,
    //          filters out all cards that do not contain the correct tags,
    //          returns filtered list
    @Override
    protected ArrayList<Card> generateStudyList(int n) {
        ArrayList<Integer> shuffleSequence = generateShuffleSequence(n);
        ArrayList<Card> list = new ArrayList<>(cards);

        for (int i = 0; i < n; i++) {
            list.set(shuffleSequence.get(i), cards.get(i));
        }

        Stream<Card> cardStream = list.stream();
        List<Card> filteredList = cardStream.filter(card -> toStudy(card))
                .collect(Collectors.toList());

        list = new ArrayList<>(filteredList);

        return list;
    }

    // EFFECTS: returns true if any of the given card's tags
    //          are in the given tag list
    private boolean toStudy(Card c) {
        ArrayList<String> cardTags = c.getTags();
        ArrayList<String> lowerCaseTags = new ArrayList<>(cardTags);
        lowerCaseTags.replaceAll(String::toLowerCase);

        for (String t : tags) {
            if (lowerCaseTags.contains(t)) {
                return true;
            }
        }
        return false;
    }
}
