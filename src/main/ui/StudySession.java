package ui;

import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static ui.QuizApp.header;

public class StudySession {
    private Deck deck;
    private ArrayList<Card> cards;
    private ArrayList<Card> studyList;
    private Scanner scanner;

    public StudySession(ArrayList<Card> cards) {
        this.cards = cards;
        studyList = generateStudyList(cards.size());
        this.scanner = new Scanner(System.in);
        beginStudySession();
    }

    // REQUIRES: n > 0
    // EFFECTS: uses shuffle sequence to randomize order of cards
    private ArrayList<Card> generateStudyList(Integer n) {
        ArrayList<Card> list = new ArrayList<>();
        ArrayList<Integer> shuffleSequence = generateShuffleSequence(n);

        list.addAll(cards);

        for (int i = 0; i < n; i++) {
            list.set(shuffleSequence.get(i), cards.get(i));
        }

        return list;
    }

    // EFFECTS: generates a random sequence of integers from 0 to n
    private static ArrayList<Integer> generateShuffleSequence(Integer n) {
        ArrayList<Integer> sequence = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            sequence.add(i);
        }
        Collections.shuffle(sequence);
        return sequence;
    }

    // EFFECTS: displays front of each card, back revealed
    private void beginStudySession() {
        header("Study Session");
        int i = 1;
        for (Card c: studyList) {
            System.out.println("\nCard " + i + ")");
            System.out.println("\tFront: " + c.getFront());
            while (true) {
                if (scanner.nextLine().equals("")) {
                    System.out.println("\tBack: " + c.getBack());
                    break;
                }
            }
            i++;
        }
    }

}
