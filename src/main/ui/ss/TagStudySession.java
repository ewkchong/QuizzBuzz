package ui.ss;

import model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ui.QuizApp.header;

// A study session that allows the user to study only the specified tags
public class TagStudySession extends StudySession {
    ArrayList<String> tags;     // list of tags to study

    // constructs a new tag study session with given cards and set of tags
    public TagStudySession(ArrayList<Card> cards, ArrayList<String> tags) {
        super(cards);
        this.tags = tags;
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

        for (String t: tags) {
            if (cardTags.contains(t)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty of card,
    //          does not affect scheduling of card
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
