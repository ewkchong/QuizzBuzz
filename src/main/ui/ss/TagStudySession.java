package ui.ss;

import model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagStudySession extends StudySession {
    ArrayList<String> tags;

    public TagStudySession(ArrayList<Card> cards, ArrayList<String> tags) {
        super(cards);
        this.tags = tags;
    }

    // REQUIRES: n > 0
    // MODIFIES: this
    // EFFECTS: uses shuffle sequence to randomize order of cards
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

    private boolean toStudy(Card c) {
        ArrayList<String> cardTags = c.getTags();

        for (String t: tags) {
            if (cardTags.contains(t)) {
                return true;
            }
        }
        return false;
    }

}
