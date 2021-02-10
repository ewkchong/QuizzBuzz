package model;

import java.util.ArrayList;
import java.util.Scanner;

import static ui.QuizApp.header;

public class Deck {
    private String title;                              // title of deck
    private ArrayList<Card> cardList;                  // all cards in deck
    private Scanner scanner = new Scanner(System.in);  // scanner for user-input

    // REQUIRES: title given must be of non-zero length
    // EFFECTS: instantiates new deck with given title
    public Deck(String title) {
        this.title = title;
        cardList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    // MODIFIES: this
    // EFFECTS: adds a new card to given deck with user-input generated properties
    public void addCard() {
        ArrayList<String> tags = new ArrayList<>();

        header("New Card:");

        System.out.println("\tFront of Card:");
        String front = scanner.nextLine();

        System.out.println("\tBack of Card:");
        String back = scanner.nextLine();

        System.out.println("Tags (optional):");
        boolean keepOpen = true;
        while (keepOpen) {
            System.out.println("\nEnter tag, or press ENTER to quit:");
            String e = scanner.nextLine();

            if (e.equals("")) {
                keepOpen = false;
            } else {
                tags.add(e);
            }
        }

        Card c = new Card(front, back, tags);
        cardList.add(c);
        System.out.println("Card added!");

    }

    // EFFECTS: prints list of cards in current deck
    public void viewCards() {
        header("Card List");
        if (cardList.size() == 0) {
            System.out.println("No cards to view!");
        } else {
            int i = 1;
            for (Card c : cardList) {
                System.out.println(i + ") ");
                c.viewCard();
                i++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes selected card from deck
    public void deleteCard() {
        System.out.println("Enter in the number of the card you would like to delete, or 0 to quit:");
        int e = scanner.nextInt();

        if (e <= cardList.size() && !(e <= 0)) {
            cardList.remove(e - 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the title of the deck to user-input string
    public void renameDeck() {
        header("Rename Deck:");
        System.out.println("Enter new name:");
        this.title = scanner.nextLine();
        System.out.println("Deck name changed!");
    }
}
